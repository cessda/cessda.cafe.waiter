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
import eu.cessda.cafe.waiter.database.DatabaseClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Java Resource class to expose /order-history end point.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/order-history")
public class OrderHistoryResource {

    @GET
    public Response getOrderHistory() {
        return Response.ok(DatabaseClass.job.values()).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderHistory(@PathParam("orderId") UUID orderId) {
        var order = DatabaseClass.job.get(orderId);
        if (order == null) {
            var errorMessage = new ApiMessage("Order Unknown");
            return Response.status(400).entity(errorMessage).build();
        } else {
            return Response.ok(order).build();
        }
    }
}
