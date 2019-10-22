package eu.cessda.cafe.waiter.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/*
 * Java Engine class to process logic on /retrieve-order/ end points
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import eu.cessda.cafe.waiter.message.RetrieveOrderMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class OrderService {

	private static final Logger log = LogManager.getLogger(OrderService.class);
    
	
	private final Map<String, Order> orderList = DatabaseClass.getOrder();
	Order order = new Order();

	// OrderService class construct
	public OrderService() {
		// No implementation yet
	}


    

    // Returns all orders from from cashier
	public List<Order> getOrder(){
		return new ArrayList<>(orderList.values());
	}


	public Order getSpecificOrder(String orderId) {
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		String now = formatter.format(date);
	
		// update Order delivery date (time)
		order.setOrderDelivered(now);
		return orderList.get(orderId);

		}	
			}
	
	   



	


