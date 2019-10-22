package eu.cessda.cafe.waiter.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.cessda.cafe.waiter.service.OrderService;


/*
 * Java Resource class to expose /retrieve/orderId end point.
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/retrieve")
public class OrderResource {
	
	OrderService orderService = new OrderService();
	
	@GET
	@Path("/{orderId}")
	public Response  getOrder(@PathParam("orderId") String orderId ) {
		
		 if(orderId == null || orderId.trim().length() == 0) {
		        return Response.serverError().entity("OrderId cannot be blank").build();
		    }
		 
		return Response.ok()
				.entity(orderService.getSpecificOrder(orderId))
				.build();
	}
}
