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


import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.database.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Java Resource class to expose /order-history end point.
 */
@RestController
@RequestMapping("/order-history")
public class OrderHistoryResource {

    private final JobRepository jobRepository;

    @Autowired
    public OrderHistoryResource(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Gets the order history of the waiter
     *
     * @return A list of all known jobs
     */
    @GetMapping
    public List<Job> getOrderHistory() {
        return jobRepository.findAll();
    }

    /**
     * Gets the order history of a specified order
     *
     * @param orderId The order to get
     * @return A list of all jobs in the order
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<List<Job>> getOrderHistory(@PathVariable("orderId") UUID orderId) {
        var jobList = jobRepository.findAllByOrderId(orderId);
        if (jobList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(jobList);
    }
}
