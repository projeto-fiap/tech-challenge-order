package tech.fiap.project.infra.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class OrderNotFoundTest {

    @Test
    void testConstructor_shouldSetCorrectValues() {
        // Arrange
        Long orderId = 789L;

        // Act
        OrderNotFound exception = new OrderNotFound(orderId);

        // Assert
        assertEquals("order.not.found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatusCode());

    }
}
