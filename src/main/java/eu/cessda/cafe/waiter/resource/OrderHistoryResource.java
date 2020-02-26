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


import eu.cessda.cafe.waiter.database.DatabaseClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Java Resource class to expose /order-history end point.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/order-history")
public class OrderHistoryResource {

    /**
     * Gets the order history of the waiter
     *
     * @return A list of all known jobs
     */
    @GET
    public Response getOrderHistory() {
        return Response.ok(DatabaseClass.getJob().values()).build();
    }

    /**
     * Gets the order history of a specified order
     *
     * @param orderId The order to get
     * @return A list of all jobs in the order
     */
    @GET
    @Path("/{orderId}")
    public Response getOrderHistory(@PathParam("orderId") UUID orderId) {
        var orders = DatabaseClass.getJob().values();
        var jobList = orders.stream().filter(job -> job.getOrderId() == orderId).collect(Collectors.toList());
        if (jobList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(jobList).build();
    }
}
