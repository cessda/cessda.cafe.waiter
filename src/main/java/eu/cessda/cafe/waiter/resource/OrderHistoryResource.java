package eu.cessda.cafe.waiter.resource;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.cessda.cafe.waiter.service.OrderHistoryService;

/*
 * Java Resource class to expose /order-history end point.
 */

@Produces(MediaType.APPLICATION_JSON)
@Path("/order-history")
public class OrderHistoryResource {
	
	private final OrderHistoryService orderHistory = new OrderHistoryService();
			
  
	@GET
	public Response getOrderHistory( ) {
		
		return Response.ok(orderHistory.getOrderHistory()).build();
	}

}
