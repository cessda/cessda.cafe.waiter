package org.cessda.cafe.waiter.database;

/*
 * HashMap to collect and hold data in Java Memory 
 */
import java.util.HashMap;
import java.util.Map;

import org.cessda.cafe.waiter.data.model.JobResponse;
import org.cessda.cafe.waiter.data.model.Machines;
import org.cessda.cafe.waiter.data.model.Order;
import org.cessda.cafe.waiter.data.model.OrderHistory;

public class DatabaseClass {
	
	private static Map<String, Machines> machine = new HashMap<>();
	private static Map<String, Order> order = new HashMap<>();
	private static Map<String, JobResponse> jobResponse = new HashMap<>();
	private static Map<String, OrderHistory> orderHistory = new HashMap<>();
	
	
	
	public static Map<String, Order> getOrder() {
		return order;
	}
	
	public static Map<String, Machines> getMachines() {
		return machine;
	}
		
	
	public static Map<String, JobResponse> getjobResponse() {
		return jobResponse;
	}
	
	public static Map<String, OrderHistory> getOrderHistory() {
		return orderHistory;
	}

}
