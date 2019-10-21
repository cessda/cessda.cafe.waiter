package eu.cessda.cafe.waiter.service;

/*
 * Java Engine class to process logic on /retrieve-order/ end points
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import eu.cessda.cafe.waiter.data.model.Machines;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.DatabaseClass;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

class OrderService {

	private static final Logger log = LogManager.getLogger(OrderService.class);

	private final Map<String, Order> orderList = DatabaseClass.getOrder();

	// OrderService class construct
	public OrderService() {
		// No implementation yet
		throw new UnsupportedOperationException();
	}

/* Engine method to retrieve data from cashier
 * TO BE DONE
 */

    private static Machines machine = new Machines();
    Client client = ClientBuilder.newClient();
    private static final String cashierUrl = machine.getCashier();

    public  Order getOrderId(){

        return client
                .target(cashierUrl)
                .request(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.APPLICATION_JSON)
                .get(Order.class);

    }

    // Returns all orders from from cashier
	public List<Order> getOrder(){
		return new ArrayList<>(orderList.values());
	}

/* Returns responses for specific order based on conditions
 * TO BE REVIEWED by Matthew
 */
	public Order getSpecificOrder() {

		for (Entry<String, Order> pair : orderList.entrySet()) {
			log.debug("key: {}, value: {}", pair.getKey(), pair.getValue());

			log.info(" this is to test the method");

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
