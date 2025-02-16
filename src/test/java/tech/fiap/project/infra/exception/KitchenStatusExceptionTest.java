package tech.fiap.project.infra.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class KitchenStatusExceptionTest {

	@Test
	void testConstructor_shouldSetCorrectValues() {
		// Arrange
		Long orderId = 456L;

		// Act
		KitchenStatusException exception = new KitchenStatusException(orderId);

		// Assert
		assertEquals("order.kitchen.status.exception", exception.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatusCode());

	}

}
