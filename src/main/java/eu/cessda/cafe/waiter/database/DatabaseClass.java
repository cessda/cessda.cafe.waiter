package eu.cessda.cafe.waiter.database;

/*
 * HashMap to collect and hold data in Java Memory 
 */

import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.data.model.Machines;
import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.data.model.OrderHistory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DatabaseClass {

	private DatabaseClass() {
		throw new IllegalStateException("Utility class");
	}

	public static final Map<String, Machines> machine = new HashMap<>();
	public static final Map<String, Order> order = new HashMap<>();
	public static final Map<UUID, Job> job = new HashMap<>();
	public static final Map<String, OrderHistory> orderHistory = new HashMap<>();


	public static Map<String, Order> getOrder() {
		return order;
	}

	public static Map<String, Machines> getMachines() {
		return machine;
	}


	public static Map<String, OrderHistory> getOrderHistory() {
		return orderHistory;
	}

}
