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

package eu.cessda.cafe.waiter.engine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.data.model.Order;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Contains logic to communicate with configured cashiers
 */
@Log4j2
public class Cashier {

    private final URL orderHistoryEndpoint;
    private final URL processedJobsEndpoint;

    public Cashier(@NonNull URL cashierUrl) {
        try {
            orderHistoryEndpoint = new URL(cashierUrl, "order-history");
            processedJobsEndpoint = new URL(cashierUrl, "processed-jobs");
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Retrieve a list of processed jobs from the cashier
     *
     * @return List of processed jobs
     * @throws IOException if a problem occurs getting the processed jobs
     */
    public List<Job> getProcessedJobs() throws IOException {
        log.info("Retrieving all processed jobs from {}.", processedJobsEndpoint);
        return new ObjectMapper().readValue(processedJobsEndpoint, new TypeReference<List<Job>>() {
        });
    }

    /**
     * Retrieve the order history from the cashier
     *
     * @return List of orders
     * @throws IOException if a problem occurs getting the orders
     */
    public List<Order> getOrderHistory() throws IOException {
        log.info("Retrieving all orders from {}.", orderHistoryEndpoint);
        return new ObjectMapper().readValue(orderHistoryEndpoint, new TypeReference<List<Order>>() {
        });
    }

    /**
     * Retrieve the order history for a specific order
     *
     * @param orderId The order to retrieve
     * @return The specified order
     * @throws IOException if a problem occurs getting the order
     */
    public Order getOrderHistory(UUID orderId) throws IOException {
        log.info("Retrieving order {} from {}.", orderId, orderHistoryEndpoint);
        try {
            var orderIdEndpoint = new URL(orderHistoryEndpoint.toString() + "/" + orderId.toString());
            return new ObjectMapper().readValue(orderIdEndpoint, new TypeReference<Order>() {
            });
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }
}
