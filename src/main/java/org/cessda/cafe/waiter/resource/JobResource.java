package org.cessda.cafe.waiter.resource;

/*
 * Java Resource class to expose /collect-jobs end point. 
 */

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cessda.cafe.waiter.service.JobService;

@Path("/collect-jobs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {
	
	private JobService jobService = new JobService();
	
	//  Return message from a  post method on /collect-jobs
	@POST
	public Response postCollectJobs() {
		
		return Response.ok(jobService.collectJobsMessage()).build();
		
	}
		

}
