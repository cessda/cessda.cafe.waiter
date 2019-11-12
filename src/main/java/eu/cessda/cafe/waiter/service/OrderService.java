/*
 * Copyright CESSDA ERIC 2019.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package eu.cessda.cafe.waiter.service;

import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import eu.cessda.cafe.waiter.engine.Cashier;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Java Engine class to process logic on /retrieve-order/ end points
 */
@Log4j2
public class OrderService {

	private URL cashierUrl;

	public OrderService() {
		try {
			cashierUrl = new URL("http://cafe-cashier:1336/");
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void getOrderService(UUID orderId) {

		log.info("Collecting orders from Cashier {}", cashierUrl);

		try {
			var orderHistory = new Cashier(cashierUrl).getOrderHistory();
			if (log.isTraceEnabled()) log.trace(orderHistory);

			for (Order order : orderHistory) {

				var orderCheck = order.getOrderId();
				// Set coffee products that correspond to orderId
				if (orderCheck != null) {
					// Update Order data persistently
					DatabaseClass.order.put(order.getOrderId(), order);
				}
				log.debug("Order database {}  updated", orderId);
			} 
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	// Returns all orders from from cashier
    public List<Order> getOrder() {
        return new ArrayList<>(DatabaseClass.order.values());
    }


    public Order getSpecificOrder(UUID orderId) {

        // update Order delivery date (time)
        var order = DatabaseClass.order.get(orderId);
        order.setOrderDelivered(new Date(System.currentTimeMillis()));
        DatabaseClass.order.put(order.getOrderId(), order);

        log.debug("Order {} Delivery updated", orderId);

        return order;
    }
}
	
	   



	


