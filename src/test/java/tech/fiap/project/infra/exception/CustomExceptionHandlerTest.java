package tech.fiap.project.infra.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CustomExceptionHandlerTest {

	private CustomExceptionHandler customExceptionHandler;

	@Mock
	private WebRequest webRequest;

	@BeforeEach
	void setUp() {
		customExceptionHandler = new CustomExceptionHandler();
		webRequest = mock(WebRequest.class);
	}

	@Test
	void shouldHandleBusinessExceptionWithoutMetadata() {
		BusinessException exception = new BusinessException("Error message", HttpStatus.BAD_REQUEST, null);

		ResponseEntity<Object> response = customExceptionHandler.handleConflict(exception, webRequest);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isInstanceOf(ExceptionResponse.class);
		ExceptionResponse body = (ExceptionResponse) response.getBody();
		assertThat(body.getMessage()).isEqualTo("Error message");
		assertThat(body.getMetadata()).isNull();
	}

	@Test
	void shouldHandleBusinessExceptionWithMetadata() {
		BusinessException exception = new BusinessException("Error message", HttpStatus.NOT_FOUND, "Some metadata");

		ResponseEntity<Object> response = customExceptionHandler.handleConflict(exception, webRequest);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isInstanceOf(ExceptionResponse.class);
		ExceptionResponse body = (ExceptionResponse) response.getBody();
		assertThat(body.getMessage()).isEqualTo("Error message");
		assertThat(body.getMetadata()).isEqualTo("Some metadata");
	}

}
