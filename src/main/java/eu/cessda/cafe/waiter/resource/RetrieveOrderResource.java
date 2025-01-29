/*
 * Copyright CESSDA ERIC 2025.
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

package eu.cessda.cafe.waiter.resource;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.database.JobRepository;
import eu.cessda.cafe.waiter.database.OrderRepository;
import eu.cessda.cafe.waiter.service.CashierConnectionException;
import eu.cessda.cafe.waiter.service.JobService;
import eu.cessda.cafe.waiter.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;


/**
 * Java Resource class to expose /retrieve-order/orderId end point.
 */
@RestController
@RequestMapping("/retrieve-order")
public class RetrieveOrderResource {
    private static final Logger log = LogManager.getLogger(RetrieveOrderResource.class);

    private static final String ORDER_UNKNOWN = "Order unknown";
    private static final String ORDER_NOT_READY = "Order not ready";
    private static final String ORDER_ALREADY_DELIVERED = "Order already delivered";

    private final JobRepository jobRepository;
    private final JobService jobService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public RetrieveOrderResource(JobRepository jobRepository, JobService jobService, OrderService orderService, OrderRepository orderRepository) {
        this.jobRepository = jobRepository;
        this.jobService = jobService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    /**
     * Retrieves the specified order
     *
     * @param orderId The order to retrieve
     * @return The retrieved order, or a message if an error occurred
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") UUID orderId) throws CashierConnectionException {

        // Is the order already delivered
        var order = orderRepository.findById(orderId).orElse(null);
        if (order == null || order.getOrderDelivered() == null) {
            // Update the orders from the cashier
            return retrieveOrderFromCashier(orderId);
        } else {
            // The order has already been delivered
            log.info("Order {} already retrieved.", order.getOrderId());
            return ResponseEntity.status(HttpStatus.GONE).body(new ApiMessage(ORDER_ALREADY_DELIVERED));
        }
    }

    /**
     * Retrieves an order from the cashier and checks the state of the order.
     * <p/>
     * This method asks the cashier to get all the jobs associated with an order, and then checks to see if all
     * the jobs associated with the order have been retrieved from the coffee machines.
     * <p/>
     * If all the jobs associated with an order have been delivered to the waiter, then the order is marked as
     * delivered and returned to the customer.
     *
     * @param orderId The id of the order to check.
     * @return A response containing the state of the order.
     */
    private ResponseEntity<?> retrieveOrderFromCashier(UUID orderId) throws CashierConnectionException {

        var order = orderService.getOrders(orderId);

        if (order != null) {

            // check conditions whether any open jobs are done and orders delivered
            order = orderRepository.findById(order.getOrderId()).orElse(order);

            // Collect all processed jobs from the cashier and retrieve them from the coffee machine
            jobService.collectJobs();

            // Does the order have all its jobs retrieved?
            boolean success = true;
            for (Job job : order.getJobs()) {
                var repositoryJob = jobRepository.findById(job.getJobId())
                        // Save the job to the local repository
                        .orElseGet(() -> jobRepository.save(job));

                if (repositoryJob.getJobRetrieved() != null) {
                    log.info("Retrieved job {} of order {}.", repositoryJob.getJobId(), order.getOrderId());
                } else {
                    log.warn("Couldn't retrieve job {} of order {}.", job.getJobId(), order.getOrderId());
                    success = false;
                }
            }
            if (success) {
                // Deliver the order
                order.setOrderDelivered(OffsetDateTime.now(ZoneId.from(ZoneOffset.UTC)));
                order = orderRepository.save(order);
                log.info("Order {} retrieved.", order.getOrderId());
                return ResponseEntity.ok().body(order);
            } else {
                // Not all jobs are retrieved
                log.info("Order {} not ready.", order.getOrderId());
                orderRepository.save(order);
                return ResponseEntity.badRequest().body(new ApiMessage(ORDER_NOT_READY));
            }
        } else {
            log.warn("The order {} was not found on the cashier.", orderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage(ORDER_UNKNOWN));
        }
    }
}
