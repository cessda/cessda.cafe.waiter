/*
 * Copyright CESSDA ERIC 2022.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package eu.cessda.cafe.waiter.resource;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.database.Database;
import eu.cessda.cafe.waiter.service.CashierConnectionException;
import eu.cessda.cafe.waiter.service.JobService;
import eu.cessda.cafe.waiter.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;


/**
 * Java Resource class to expose /retrieve-order/orderId end point.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/retrieve-order")
public class RetrieveOrderResource {
    private static final Logger log = LogManager.getLogger(RetrieveOrderResource.class);

    private static final String ORDER_UNKNOWN = "Order unknown";
    private static final String ORDER_NOT_READY = "Order not ready";
    private static final String ORDER_ALREADY_DELIVERED = "Order already delivered";

    private final JobService jobService;
    private final OrderService orderService;
    private final Database database;

    @Inject
    public RetrieveOrderResource(JobService jobService, OrderService orderService, Database database) {
        this.jobService = jobService;
        this.orderService = orderService;
        this.database = database;
    }

    /**
     * Retrieves the specified order
     *
     * @param orderId The order to retrieve
     * @return The retrieved order, or a message if an error occurred
     */
    @GET
    @Path("/{orderId}")
    public Response getOrder(@PathParam("orderId") UUID orderId, @Context HttpHeaders requestHeaders) {

        // Is the order already delivered
        var order = database.getOrder().get(orderId);
        if (order == null || order.getOrderDelivered() == null) {
            // Update the orders from the cashier
            try {
                return retrieveOrderFromCashier(orderId);
            } catch (CashierConnectionException e) {
                log.error(e);
                return Response.serverError().entity(new ApiMessage(e.getMessage())).build();
            }
        } else {
            // The order has already been delivered
            log.info("Order {} already retrieved.", order.getOrderId());
            return Response.status(Response.Status.GONE).entity(new ApiMessage(ORDER_ALREADY_DELIVERED)).build();
        }
    }

    /**
     * Retrieves an order from the cashier and checks the state of the order.
     * <p/>
     * This method asks the cashier to get all the jobs associated with an order, and then checks to see if all
     * the jobs associated with the order have been retrieved from the coffee machines.
     * <p/>
     * If all the jobs associated with an order have been delivered to the waiter, then the order is marked as
     * delivered and returned to the customer.
     *
     * @param orderId The id of the order to check.
     * @return A response containing the state of the order.
     */
    private Response retrieveOrderFromCashier(UUID orderId) throws CashierConnectionException {

        var order = orderService.getOrders(orderId);

        if (order != null) {

            // check conditions whether any open jobs are done and orders delivered
            database.getOrder().putIfAbsent(order.getOrderId(), order);

            // Collect all processed jobs from the cashier and retrieve them from the coffee machine
            jobService.collectJobs();

            // Does the order have all its jobs retrieved?
            boolean success = true;
            for (Job job : order.getJobs()) {
                var retrievedJob = database.getJob().get(job.getJobId());
                if (retrievedJob != null) {
                    log.info("Retrieved job {} of order {}.", retrievedJob.getJobId(), order.getOrderId());
                } else {
                    log.warn("Couldn't retrieve job {} of order {}.", job.getJobId(), order.getOrderId());
                    success = false;
                }
            }
            if (success) {
                // Deliver the order
                order.setOrderDelivered(OffsetDateTime.now(ZoneId.from(ZoneOffset.UTC)));
                log.info("Order {} retrieved.", order.getOrderId());
                return Response.ok().entity(order).build();
            } else {
                // Not all jobs are retrieved
                log.info("Order {} not ready.", order.getOrderId());
                return Response.status(Response.Status.BAD_REQUEST).entity(new ApiMessage(ORDER_NOT_READY)).build();
            }
        } else {
            log.warn("The order {} was not found on the cashier.", orderId);
            return Response.status(Response.Status.NOT_FOUND).entity(new ApiMessage(ORDER_UNKNOWN)).build();
        }
    }
}
