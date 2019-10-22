package eu.cessda.cafe.waiter.service;

/*
 * Java Engine class to process logic on /retrieve-order/ end points
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.DatabaseClass;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class OrderService {

	private static final Logger log = LogManager.getLogger(OrderService.class);

	private final Map<String, Order> orderList = DatabaseClass.getOrder();

	// OrderService class construct
	public OrderService() {
		// No implementation yet
		throw new UnsupportedOperationException();
	}


    

    // Returns all orders from from cashier
	public List<Order> getOrder(){
		return new ArrayList<>(orderList.values());
	}

/* Returns responses for specific order based on conditions
 * 
 */
	public Response getSpecificOrder(String orderId) {

		// Iterate through the object 	and return message based orderId, OrderSize and OrderDelivered TimeStamp
		String condition1 = " Order Unknown";
		String condition2 = " Order Not Ready";
		String condition3 = " Order Already Delivered";
		
		boolean ans = orderList.containsValue(orderId);
		
	    Iterator<Map.Entry<String, Order>> orderIt = orderList.entrySet().iterator();
	    
	    return null;

	   }



	}


