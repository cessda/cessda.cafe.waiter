package eu.cessda.cafe.waiter.resource;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthCheckResourceTest {

	@Test
	void shouldReturnHealthy() {
		// Act
		var message = new HealthCheckResource().getHealth();

		// The API message should be healthy
		assertEquals(ApiMessage.healthy(), message);
	}
}