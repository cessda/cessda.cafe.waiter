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

import eu.cessda.cafe.waiter.TestData;
import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.database.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderHistoryResourceTest {

	private final JobRepository jobRepository;

	private final UUID orderId = UUID.randomUUID();
	private final UUID jobId = UUID.randomUUID();

	OrderHistoryResourceTest() {
		 jobRepository = new TestData(orderId, jobId);

		var order = new Order();
		order.setJobs(Set.copyOf(jobRepository.findAllByOrderId(orderId)));
	}

	@Test
	void shouldRetrieveEntireOrderHistory() {

        var jobHistory = new OrderHistoryResource(jobRepository).getOrderHistory();

		assertFalse(jobHistory.isEmpty());
		assertTrue(jobHistory.stream().anyMatch(job -> job.getJobId().equals(jobId)));
	}

	@Test
	void shouldRetrieveJobsOfASpecificOrder() {
		var orderHistoryResponse = new OrderHistoryResource(jobRepository).getOrderHistory(orderId);

		var jobHistory = orderHistoryResponse.getBody();

		assertNotNull(jobHistory);
		assertFalse(jobHistory.isEmpty());
		assertTrue(jobHistory.stream().anyMatch(job -> job.getJobId().equals(jobId)));
	}

	@Test
	void shouldReturnNotFoundOnInvalidOrder() {
		var orderHistoryResponse = new OrderHistoryResource(jobRepository).getOrderHistory(UUID.randomUUID());
		assertEquals(HttpStatus.NOT_FOUND, orderHistoryResponse.getStatusCode());
	}
}