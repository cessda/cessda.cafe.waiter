package org.cessda.cafe.waiter.message;

/*
 * Class TO BE DELETED !!!!
 */

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OrderExceptionMapper implements ExceptionMapper<OrderResponseException> {

	@Override
	public Response toResponse(OrderResponseException exception) {
		// TODO Auto-generated method stub
	//	OrderException orderException = new OrderException ("message: Order Unknown ", 400);
		return Response.status(400)
		//		.entity(orderException)
				.build();
	}

}
