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
import eu.cessda.cafe.waiter.exceptions.CashierConnectionException;
import eu.cessda.cafe.waiter.resource.ApplicationPathResource;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Java Engine class to process logic on /retrieve-order/ end points
 */
@Log4j2
public class OrderService {

    private final URL cashierUrl;

	public OrderService() {
		try {
            cashierUrl = new URL(ApplicationPathResource.CASHIER_URL);
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
	}

    public void getOrders() throws CashierConnectionException {

        log.info("Collecting orders from Cashier {}.", cashierUrl);

        try {
            var orderHistory = new Cashier(cashierUrl).getOrderHistory();
            if (log.isTraceEnabled()) log.trace(orderHistory);

            for (Order order : orderHistory) {
                // Update Order data persistently
                DatabaseClass.order.putIfAbsent(order.getOrderId(), order);
            }

        } catch (IOException e) { // Send the exception up so a 500 can be generated
            log.error("Error connecting to cashier {}: {}", cashierUrl, e);
            throw CashierConnectionException.exceptionMessage(cashierUrl, e);
        }
    }

    public void getOrders(UUID orderId) throws CashierConnectionException, FileNotFoundException {
        log.info("Collecting order {} from Cashier {}.", orderId, cashierUrl);
        try {
            // Collect only the specified order
            var order = new Cashier(cashierUrl).getOrderHistory(orderId);
            DatabaseClass.order.putIfAbsent(order.getOrderId(), order);
        } catch (FileNotFoundException e) {
            log.warn("The order {} was not found on the cashier.", orderId);
            throw e;
        } catch (IOException e) { // Send the exception up so a 500 can be generated
            log.error("Error connecting to cashier {}: {}", cashierUrl, e);
            throw CashierConnectionException.exceptionMessage(cashierUrl, e);
        }
    }
}
	
	   



	


