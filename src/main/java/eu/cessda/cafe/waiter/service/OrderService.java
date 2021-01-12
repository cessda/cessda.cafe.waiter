/*
 * Copyright CESSDA ERIC 2020.
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

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cessda.cafe.waiter.WaiterApplication;
import eu.cessda.cafe.waiter.data.model.Order;
import lombok.extern.log4j.Log4j2;
import org.jvnet.hk2.annotations.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

/**
 * Java Engine class to process logic on /retrieve-order/ end points
 */
@Log4j2
@Service
public class OrderService {

    private final ObjectMapper objectMapper;

    private final URI orderHistoryEndpoint;

    /**
     * Sets the cashier URL used for all further methods in this class
     */
    @Inject
    public OrderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        orderHistoryEndpoint = WaiterApplication.getCashierUrl().resolve("order-history/");
    }

    /**
     * Gets the order history for an order with a specific orderId from the cashier. If the order was not present
     * on the cashier, then {@code null} is returned.
     *
     * @param orderId The orderId to get
     * @return the order associated with the order ID, or {@code null} if the order was not present on the cashier
     * @throws CashierConnectionException if a connection error occurred connecting to the cashier
     */
    @Nullable
    public Order getOrders(UUID orderId) throws CashierConnectionException {
        log.info("Collecting order {} from Cashier {}.", orderId, WaiterApplication.getCashierUrl());
        try {
            // Collect only the specified order
            log.info("Retrieving order {} from {}.", orderId, orderHistoryEndpoint);
            var orderIdEndpoint = orderHistoryEndpoint.resolve(orderId.toString());
            return objectMapper.readValue(orderIdEndpoint.toURL(), Order.class);
        } catch (FileNotFoundException e) {
            // The order was not found
            return null;
        } catch (IOException e) {
            // Send the exception up so a 500 can be generated
            throw new CashierConnectionException(WaiterApplication.getCashierUrl(), e);
        }
    }
}
	
	   



	


