package eu.cessda.cafe.waiter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.cessda.cafe.waiter.data.model.Machines;
import eu.cessda.cafe.waiter.data.model.OrderHistory;
import eu.cessda.cafe.waiter.data.model.Product;
import eu.cessda.cafe.waiter.database.DatabaseClass;

public class OrderHistoryService {
	Machines machine = new Machines();
	String coffeeMachine = machine.getCoffeeMachine();
	Product product1 = Product.CAPPUCCINO;
	Product product2 = Product.COFFEE;
	Product product3 = Product.KAKAO;
	
	private final Map<String, OrderHistory> orderHistory = DatabaseClass.getOrderHistory();

	public OrderHistoryService() {
		
		orderHistory.put("00000000-FFFF-FFFF-FFFF-000000000000", new OrderHistory("00000000-FFFF-FFFF-FFFF-000000000000","00000000-2222-2222-2222-000000000000",new Product[] {product3,product3}, "2019-07-31T01:00:00.000Z","2019-07-31T01:00:01.000Z", 2, coffeeMachine,"2019-07-31T01:00:01.000Z","2019-07-31T01:00:32.000Z"));
		orderHistory.put("00000000-AAAA-AAAA-AAAA-000000000000", new OrderHistory("00000000-AAAA-AAAA-AAAA-000000000000","00000000-2222-2222-2222-000000000000",new Product[] {product3,product2}, "2019-07-31T01:00:00.000Z","2019-07-31T01:00:01.000Z", 2, coffeeMachine,"2019-07-31T01:00:01.000Z","2019-07-31T01:00:32.000Z"));
		orderHistory.put("00000000-BBBB-BBBB-BBBB-000000000000", new OrderHistory("00000000-BBBB-BBBB-BBBB-000000000000","00000000-2222-2222-2222-000000000000",new Product[] {product1}, "2019-07-31T01:00:00.000Z","2019-07-31T01:00:01.000Z", 1, coffeeMachine,"2019-07-31T01:00:01.000Z","2019-07-31T01:00:32.000Z"));
	}
	
    public List<OrderHistory> getOrderHistory(){
		
		return new ArrayList<>(orderHistory.values());
	}
    
    public OrderHistory getSpecificOrderHistory(String orderId) {
    	
    	return orderHistory.get(orderId);
    }
    
   }
	
	

    
	


