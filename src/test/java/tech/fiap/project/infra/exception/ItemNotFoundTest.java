package tech.fiap.project.infra.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class ItemNotFoundTest {

    @Test
    void testConstructor_shouldSetCorrectValues() {
        // Arrange
        Long itemId = 123L;

        // Act
        ItemNotFound exception = new ItemNotFound(itemId);

        // Assert
        assertEquals("item not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatusCode());

    }
}
