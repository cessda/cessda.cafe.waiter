package eu.cessda.cafe.waiter.resource;

/*
 * Java Resource class to expose /collect-jobs end point. 
 */

import eu.cessda.cafe.waiter.service.JobService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/collect-jobs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {
	
	private final JobService jobService = new JobService();
	
	//  Return message from a  post method on /collect-jobs
	@POST
	public Response postCollectJobs() {
		// TODO: Cashier code

        return Response.ok(jobService.collectJobs()).build();
		
	}
}
