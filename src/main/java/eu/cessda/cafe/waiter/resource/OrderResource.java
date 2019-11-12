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
import eu.cessda.cafe.waiter.service.JobService;
import eu.cessda.cafe.waiter.service.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/*
 * Java Resource class to expose /retrieve/orderId end point.
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/retrieve-order")
public class OrderResource {

    private static final String ORDER_UNKNOWN = "Order Unknown";
    private static final Map<UUID, Order> orderList = DatabaseClass.order;
    Order order = new Order();
    private static final String ORDER_NOT_READY = "Order Not Ready";
    private static final String ORDER_ALREADY_DELIVERED = "Order Already Delivered";
    private final  OrderService orderService = new OrderService();

    @GET
    @Path("/{orderId}")
    public Response getSpecificOrder(@PathParam("orderId") UUID orderId) {

        if (orderId == null) {
            var invalidOrderIdMessage = new ApiMessage("OrderId cannot be blank");
            return Response.serverError().entity(invalidOrderIdMessage).build();
        }
        
        orderService.getOrderService(orderId);
        try {
            new JobService().collectJobs();
        } catch (CashierConnectionException e) {
            return Response.serverError().entity(new ApiMessage(e.getMessage())).build();
        }

        /* Returns responses for specific order based on conditions
         *
         */

        boolean ans = orderList.containsKey(orderId);
        var order = orderList.get(orderId);


        // check conditions whether any open jobs are done and orders delivered

        if (!ans) {
            var orderUnknownMessage = new ApiMessage(ORDER_UNKNOWN);
            return Response
                    .status(400)
                    .entity(orderUnknownMessage)
                    .build();
        } else {
            boolean success = false;
            for (var job : order.getJobs()) {
                if (job != null) {
                    var retrivedJob = DatabaseClass.job.get(job.getJobId());
                    if (retrivedJob != null) {
                        success = true;
                    }
                }
            }
            if (!success) {
                var orderNotReadyMessage = new ApiMessage(ORDER_NOT_READY);
                return Response
                        .status(400)
                        .entity(orderNotReadyMessage)
                        .build();
            }
            if (order.getOrderDelivered() != null) {
                var orderAlreadyDeliveredMessage = new ApiMessage(ORDER_ALREADY_DELIVERED);
                return Response
                        .status(400)
                        .entity(orderAlreadyDeliveredMessage)
                        .build();
            } else {
                order.setOrderDelivered(new Date());
                DatabaseClass.order.put(order.getOrderId(), order);
                return Response.ok().entity(order).build();
            }
        }
    }

    @GET
    public List<Order> getAllOrder() {

        return orderService.getOrder();
    }
}
