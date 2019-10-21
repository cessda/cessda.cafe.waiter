package eu.cessda.cafe.waiter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.cessda.cafe.waiter.data.model.OrderHistory;
import eu.cessda.cafe.waiter.database.DatabaseClass;

public class OrderHistoryService {
	
	
	
	private final Map<String, OrderHistory> orderHistory = DatabaseClass.getOrderHistory();

	public OrderHistoryService() {
		// No implementation yet
		throw new UnsupportedOperationException();
	}
	
public List<OrderHistory> getOrderHistory(){
		
		return new ArrayList<>(orderHistory.values());
	}
	
	

	

}
