package eu.cessda.cafe.waiter.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eu.cessda.cafe.waiter.data.model.Machines;
import eu.cessda.cafe.waiter.service.MachineService;

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
		return Response.ok(machineService.getMachines())
				.build();
	}
	
//   Configure end-points for Coffee machine and Cashier 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMachine(Machines machine) {
		machineService.postMachines(machine);
		return Response
				.status(201)
				.entity(machine)
				.build();
	}
	
//  Remove end-points for configured Coffee machine and Cashier	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Status deleteMachines() {
	
		machineService.deleteMachines();
		return Response.Status.GONE;
	}
	


}
