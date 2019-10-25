package eu.cessda.cafe.waiter.resource;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import eu.cessda.cafe.waiter.service.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;


/*
 * Java Resource class to expose /retrieve/orderId end point.
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/retrieve-order")
public class OrderResource {

	private static final String ORDER_UNKNOWN = "Order Unknown";
	private final Map<String, Order> orderList = DatabaseClass.getOrder();
    Order order = new Order();
	private static final String ORDER_NOT_READY = "Order Not Ready";
	private static final String ORDER_ALREADY_DELIVERED = "Order Already Delivered";
	private OrderService orderService = new OrderService();

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

		if (!ans) {
			var message1 = new ApiMessage(ORDER_UNKNOWN);
				return Response
						.status(400)
						.entity(message1)
						.build();	
			} else {

			if (orderList.get(orderId).getCoffees().length != orderList.get(orderId).getOrdersize()) {
				var message2 = new ApiMessage(ORDER_NOT_READY);
					return Response
							.status(400)
							.entity(message2)
							.build();	
				} else  {
				if (!orderList.get(orderId).getOrderDelivered().equals("")) {
					var message3 = new ApiMessage(ORDER_ALREADY_DELIVERED);
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
