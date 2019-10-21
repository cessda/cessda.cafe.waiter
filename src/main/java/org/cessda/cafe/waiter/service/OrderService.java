package org.cessda.cafe.waiter.service;

/*
 * Java Engine class to process logic on /retrieve-order/ end points 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cessda.cafe.waiter.data.model.Order;
import org.cessda.cafe.waiter.database.DatabaseClass;

public class OrderService {
	
private Map<String, Order> orderList = DatabaseClass.getOrder();

// OrderService class construct 
	public OrderService() {
		
	}

/* Engine method to retrieve data from cashier
 * TO BE DONE   	
 */
	
// Returns all orders from from cashier 	
	public List<Order> getOrder(){
		return new ArrayList<Order>(orderList.values());
	}
	
/* Returns responses for specific order based on conditions
 * TO BE REVIEWED by Matthew   
 */
	public Order getSpecificOrder() {	
		Iterator<Map.Entry<String, Order>> orderIt = orderList.entrySet().iterator();
		
		while (orderIt.hasNext()) {
			 Entry<String, Order> pair = orderIt.next();
			 
			 System.out.format("key: %s, value: %d%n", pair.getKey(), 
	                    pair.getValue());
			 
			 System.out.println(" this is to test the method");
			 
		}
		
		return null;
		
		/*
		for(int i = 0; i < orderList.size(); i++) {		
			if (order.orderId.equals(orderId)) {
				if (order.orderDelivered.equals(null) || order.ordersize ==  order.coffees.getProduct().length) {
					
				}
			} 	
			
		}
		
	*/	
	
		
		
		
	}

}
