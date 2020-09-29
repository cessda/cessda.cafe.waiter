/*
 * Copyright CESSDA ERIC 2020.
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
import eu.cessda.cafe.waiter.exceptions.CashierConnectionException;
import eu.cessda.cafe.waiter.message.RequestListener;
import eu.cessda.cafe.waiter.service.JobService;
import eu.cessda.cafe.waiter.service.OrderService;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;


/**
 * Java Resource class to expose /retrieve-order/orderId end point.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/retrieve-order")
@Log4j2
public class RetrieveOrderResource {

    private static final String ORDER_UNKNOWN = "Order unknown";
    private static final String ORDER_NOT_READY = "Order not ready";
    private static final String ORDER_ALREADY_DELIVERED = "Order already delivered";

    private final RequestListener requestListener = new RequestListener();
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

        List<String> requestHeader = requestHeaders.getRequestHeader("X-Request-Id");
        String requestId;

        if (requestHeader == null) {
            requestId = UUID.randomUUID().toString();
        } else {
            requestId = requestHeader.get(0);
        }

        requestListener.requestInitialized(requestId);

        if (orderId == null) {
            log.warn("OrderId Invalid.");
            return Response.status(Response.Status.BAD_REQUEST).entity(new ApiMessage("Invalid orderId")).build();
        } else {// Is the order already delivered
            var order = database.getOrder().get(orderId);
            if (order != null && order.getOrderDelivered() != null) {
                // The order has already been delivered
                log.info("Order {} already retrieved.", order.getOrderId());
                return Response.status(Response.Status.GONE).entity(new ApiMessage(ORDER_ALREADY_DELIVERED)).build();
            } else {
                // Update the orders from the cashier
                return retrieveOrderFromCashier(orderId);
            }
        }
    }

    /**
     * Retrieves an order from the cashier and checks the state of the order.
     * <p/>
     * This method asks the cashier to get all of the jobs associated with an order, and then checks to see if all
     * the jobs associated with the order have been retrieved from the coffee machines.
     * <p/>
     * If all of the jobs associated with an order have been delivered to the waiter, then the order is marked as
     * delivered and returned to the customer.
     *
     * @param orderId The id of the order to check.
     * @return A response containing the state of the order.
     */
    private Response retrieveOrderFromCashier(UUID orderId) {
        try {
            var order = orderService.getOrders(orderId);

            // check conditions whether any open jobs are done and orders delivered
            database.getOrder().put(order.getOrderId(), order);

            // Collect all processed jobs from the cashier and retrieve them from the coffee machine
            jobService.collectJobs();

            // Does the order have all it's jobs retrieved?
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
        } catch (CashierConnectionException e) {
            return Response.serverError().entity(new ApiMessage(e.getMessage())).build();
        } catch (FileNotFoundException e) {
            log.warn("Order {} unknown.", orderId);
            return Response.status(Response.Status.NOT_FOUND).entity(new ApiMessage(ORDER_UNKNOWN)).build();
        }
    }
}
