package eu.cessda.cafe.waiter.database;

/*
 * HashMap to collect and hold data in Java Memory 
 */
import java.util.HashMap;
import java.util.Map;

import eu.cessda.cafe.waiter.data.model.JobResponse;
import eu.cessda.cafe.waiter.data.model.Machines;
import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.data.model.OrderHistory;

public class DatabaseClass {

	private DatabaseClass() {
		throw new IllegalStateException("Utility class");
	}
	
	private static final Map<String, Machines> machine = new HashMap<>();
	private static final Map<String, Order> order = new HashMap<>();
	private static final Map<String, JobResponse> jobResponse = new HashMap<>();
	private static final Map<String, OrderHistory> orderHistory = new HashMap<>();
	
	
	
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
