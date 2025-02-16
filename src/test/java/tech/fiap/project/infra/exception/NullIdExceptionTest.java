package tech.fiap.project.infra.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class NullIdExceptionTest {

	@Test
	void testConstructor_shouldSetCorrectValues() {
		// Act
		NullIdException exception = new NullIdException();

		// Assert
		assertEquals("item.id.null", exception.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatusCode());

	}

}
