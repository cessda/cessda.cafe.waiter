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

import eu.cessda.cafe.waiter.data.model.Machines;
import eu.cessda.cafe.waiter.service.MachineService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/*
 * Java Resource class to expose /configure end point.
 */

@Path("/configure")
public class MachineResource {

    private final MachineService machineService = new MachineService();

    //  Returns the configured end-points for Coffee machine and Cashier
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMachines() {
        return Response.ok(machineService.getMachines()).build();
    }

    //   Configure end-points for Coffee machine and Cashier
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMachine(Machines machine) {
        machineService.postMachines(machine.getCashier());
        return Response.status(201).entity(machine).build();
    }

    //  Remove end-points for configured Coffee machine and Cashier
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Status deleteMachines() {
        machineService.deleteMachines(null);
        return Response.Status.GONE;
    }
}
