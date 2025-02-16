package tech.fiap.project.infra.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionResponseTest {

	@Test
	void shouldCreateExceptionResponseWithAllFields() {
		ExceptionResponse response = new ExceptionResponse("Error occurred", 400, "BAD_REQUEST", "Some metadata");

		assertThat(response.getMessage()).isEqualTo("Error occurred");
		assertThat(response.getCode()).isEqualTo(400);
		assertThat(response.getStatus()).isEqualTo("BAD_REQUEST");
		assertThat(response.getMetadata()).isEqualTo("Some metadata");
	}

	@Test
	void shouldConvertToStringCorrectly() {
		ExceptionResponse response = new ExceptionResponse("Error occurred", 400, "BAD_REQUEST", "Some metadata");

		String expectedString = "{message:'Error occurred', code:'400, status:'BAD_REQUEST', metadata:'Some metadata'}";

		assertThat(response.toString()).isEqualTo(expectedString);
	}

}
