/*
 * Copyright CESSDA ERIC 2022.
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
import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.data.model.Product;
import eu.cessda.cafe.waiter.database.Database;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderHistoryResourceTest {

	private final Database database = new Database();

	private final UUID orderId = UUID.randomUUID();
	private final UUID jobId = UUID.randomUUID();

	OrderHistoryResourceTest() {

		var job = new Job();
		job.setJobId(jobId);
		job.setOrderId(orderId);
		job.setProduct(Product.COFFEE);
		job.setOrderPlaced(OffsetDateTime.now().minusHours(1));
		job.setMachine(URI.create("http://localhost:1336"));
		job.setJobStarted(OffsetDateTime.now().minusMinutes(16));

		var order = new Order();
		order.setJobs(Collections.singletonList(job));

		database.getJob().put(job.getJobId(), job);
		database.getOrder().put(orderId, order);
	}

	@SuppressWarnings("unchecked")
	@Test
	void shouldRetrieveEntireOrderHistory() {
		try (var orderHistoryResponse = new OrderHistoryResource(database).getOrderHistory()) {

			var jobHistory = (Collection<Job>) orderHistoryResponse.getEntity();

			assertFalse(jobHistory.isEmpty());
			assertTrue(jobHistory.stream().anyMatch(job -> job.getJobId().equals(jobId)));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	void shouldRetrieveJobsOfASpecificOrder() {
		try (var orderHistoryResponse = new OrderHistoryResource(database).getOrderHistory(orderId)) {

			var jobHistory = (Collection<Job>) orderHistoryResponse.getEntity();

			assertFalse(jobHistory.isEmpty());
			assertTrue(jobHistory.stream().anyMatch(job -> job.getJobId().equals(jobId)));
		}
	}

	@Test
	void shouldReturnNotFoundOnInvalidOrder() {
		try (var orderHistoryResponse = new OrderHistoryResource(database).getOrderHistory(UUID.randomUUID())) {
			assertEquals(Response.Status.NOT_FOUND.getStatusCode(), orderHistoryResponse.getStatus());
		}
	}
}