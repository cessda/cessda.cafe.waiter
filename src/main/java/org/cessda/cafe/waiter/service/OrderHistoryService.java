package org.cessda.cafe.waiter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cessda.cafe.waiter.data.model.OrderHistory;
import org.cessda.cafe.waiter.database.DatabaseClass;

public class OrderHistoryService {
	
	
	
	private Map<String, OrderHistory> orderHistory = DatabaseClass.getOrderHistory();

	public OrderHistoryService() {
		
	}
	
public List<OrderHistory> getOrderHistory(){
		
		return new ArrayList<OrderHistory>(orderHistory.values());
	}
	
	

	

}
