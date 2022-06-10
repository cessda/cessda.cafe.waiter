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

/*
 * Java Resource class to expose /collect-jobs end point.
 */

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.service.CashierConnectionException;
import eu.cessda.cafe.waiter.service.JobService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/collect-jobs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {

    private final JobService jobService;

    @Inject
    public JobResource(JobService jobService) {
        this.jobService = jobService;
    }

    // Return message from a post method on /collect-jobs
    @POST
    public Response postCollectJobs() {
        try {
            return Response.ok(jobService.collectJobs()).build();
        } catch (CashierConnectionException e) { // In the case that the cashier cannot be contacted
            return Response.serverError().entity(new ApiMessage(e.getMessage())).build();
        }
    }
}
