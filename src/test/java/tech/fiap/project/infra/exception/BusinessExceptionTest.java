package tech.fiap.project.infra.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessExceptionTest {

	@Test
	void shouldCreateExceptionWithHttpStatusAndMetadata() {
		BusinessException exception = new BusinessException("Error occurred: %s", HttpStatus.BAD_REQUEST,
				"Invalid input", "Field X");

		assertThat(exception.getMessage()).isEqualTo("Error occurred: Field X");
		assertThat(exception.getHttpStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(exception.getMetadata()).isEqualTo("Invalid input");
	}

	@Test
	void shouldCreateExceptionWithCause() {
		Throwable cause = new RuntimeException("Root cause");
		BusinessException exception = new BusinessException("Error key", cause);

		assertThat(exception.getMessage()).isEqualTo("Error key");
		assertThat(exception.getCause()).isEqualTo(cause);
		assertThat(exception.getHttpStatusCode()).isNull();
		assertThat(exception.getMetadata()).isNull();
	}

}