package eu.cessda.cafe.waiter.resource;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import eu.cessda.cafe.waiter.message.RetrieveOrderMessage;
import eu.cessda.cafe.waiter.service.OrderService;


/*
 * Java Resource class to expose /retrieve/orderId end point.
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/retrieve-order")
public class OrderResource {
	
	OrderService orderService = new OrderService();
	private final Map<String, Order> orderList = DatabaseClass.getOrder();
    Order order = new Order();
	String condition1 = " Message:Order Unknown";
	String condition2 = " Message:Order Not Ready";
	String condition3 = " Message:Order Already Delivered";

	@GET
	@Path("/{orderId}")
	public Response  getSpecificOrder(@PathParam("orderId") String orderId ) {
	
		 if(orderId == null || orderId.trim().length() == 0) {
		        return Response.serverError().entity("OrderId cannot be blank").build();
		    }
		
			
/* Returns responses for specific order based on conditions
 * 
*/
		 
	      boolean ans = orderList.containsKey(orderId);
			
			
		// check conditions whether any open jobs are done and orders delivered	
		
			if (ans == false) {
				RetrieveOrderMessage message1 = new RetrieveOrderMessage(condition1);
				return Response
						.status(400)
						.entity(message1)
						.build();	
			} else {
				
				if ( orderList.get(orderId).getCoffees().length != orderList.get(orderId).getOrdersize()) {
					RetrieveOrderMessage message2 = new RetrieveOrderMessage(condition2);
					return Response
							.status(400)
							.entity(message2)
							.build();	
				} else  {
					if (orderList.get(orderId).getOrderDelivered() != "") {
					RetrieveOrderMessage message3 = new RetrieveOrderMessage(condition3);	
					return Response
							.status(400)
							.entity(message3)
							.build();
					} else {  
						return Response.ok()
								.entity(orderService.getSpecificOrder(orderId))
								.build();
					} 
			} 

				
		}
	}
	
	@GET
	public List<Order>  getAllOrder() {
		 
		return orderService.getOrder();
	}
}
