/*
 * Copyright CESSDA ERIC 2019.
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
import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import eu.cessda.cafe.waiter.exceptions.CashierConnectionException;
import eu.cessda.cafe.waiter.message.RequestListener;
import eu.cessda.cafe.waiter.service.JobService;
import eu.cessda.cafe.waiter.service.OrderService;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.UUID;


/*
 * Java Resource class to expose /retrieve/orderId end point.
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/retrieve-order")
@Log4j2
public class OrderResource {

    private static final String ORDER_UNKNOWN = "Order Unknown";
    private static final String ORDER_NOT_READY = "Order not ready";
    private static final String ORDER_ALREADY_DELIVERED = "Order already delivered";
    RequestListener requestListener = new RequestListener();

    /**
     * Retrieves the specified order
     *
     * @param orderId The order to retrieve
     * @return The retrieved order, or a message if an error occurred
     */
    @GET
    @Path("/{orderId}")
    public Response getOrder(
    		@PathParam("orderId") UUID orderId,
    		@HeaderParam("X-Request-Id") String requestId) {
    	
    	requestListener.requestInitialized(requestId);
        if (orderId == null) {
            return Response.status(400).entity(new ApiMessage("Invalid orderId")).build();
        }

        // Is the order already delivered
        var order = DatabaseClass.order.get(orderId);
        if (order != null && order.getOrderDelivered() != null) {
            // The order has already been delivered
            log.info("Order {} already retrieved.", order.getOrderId());
            return Response.status(400).entity(new ApiMessage(ORDER_ALREADY_DELIVERED)).build();
        }

        try {
            new OrderService().getOrders(orderId);
            new JobService().collectJobs();
        } catch (CashierConnectionException e) {
            return Response.serverError().entity(new ApiMessage(e.getMessage())).build();
        } catch (FileNotFoundException e) {
            return Response.status(400).entity(new ApiMessage(ORDER_UNKNOWN)).build();
        }

        order = DatabaseClass.order.get(orderId);

        // check conditions whether any open jobs are done and orders delivered

        if (order == null) {
            // The order doesn't exist
            return Response.status(400).entity(new ApiMessage(ORDER_UNKNOWN)).build();
        } else {
            // The order exists, find what state it's in
            return getOrderState(order);
            
        }
        
    }
    

    private Response getOrderState(Order order) {
        // Does the order have all it's jobs retrieved
        boolean success = true;
        for (var job : order.getJobs()) {
            var retrievedJob = DatabaseClass.job.get(job.getJobId());
            if (retrievedJob == null) {
                log.warn("Couldn't retrieve job {} of order {}.", job.getJobId(), order.getOrderId());
                success = false;
            } else {
                log.info("Retrieved job {} of order {}.", retrievedJob.getJobId(), order.getOrderId());
            }
        }
        if (!success) {
            // Not all jobs are retrieved
            return Response.status(400).entity(new ApiMessage(ORDER_NOT_READY)).build();
        } else {
            // Deliver the order
            order.setOrderDelivered(new Date());
            DatabaseClass.order.replace(order.getOrderId(), order);
            log.info("Order {} retrieved.", order.getOrderId());
            requestListener.requestDestroyed();
            return Response.ok().entity(order).build();
            
        }
    }
}
