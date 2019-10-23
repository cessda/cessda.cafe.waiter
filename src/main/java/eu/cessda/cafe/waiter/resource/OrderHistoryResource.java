package eu.cessda.cafe.waiter.resource;


import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import eu.cessda.cafe.waiter.message.RetrieveOrderMessage;
import eu.cessda.cafe.waiter.service.OrderHistoryService;

/*
 * Java Resource class to expose /order-history end point.
 */

@Produces(MediaType.APPLICATION_JSON)
@Path("/order-history")
public class OrderHistoryResource {
	
	private final Map<String, Order> orderHistory = DatabaseClass.getOrder();
	String message = " Message:Order Unknown";
	OrderHistoryService historyService = new OrderHistoryService (); 
  
	@GET
	public Response getAllOrderHistory( ) {
		
		return Response.ok(historyService.getOrderHistory()).build();
	}
	
	@GET
	@Path("/{orderId}")
	public Response getOrderHistory(@PathParam("orderId") String orderId ) {

		boolean ans = orderHistory.containsKey(orderId);
    	if (ans == false) {
    		RetrieveOrderMessage errorMessage = new RetrieveOrderMessage(message);	
		    return Response
				.status(400)
				.entity(errorMessage)
				.build();
    	} else {
    		return Response
    				.ok(historyService.getSpecificOrderHistory(orderId))
    				.build();
    		
    	}
	}


}
