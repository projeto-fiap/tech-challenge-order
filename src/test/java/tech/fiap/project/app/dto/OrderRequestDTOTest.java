package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRequestDTOTest {

	@Test
	void testOrderRequestDTOConstructorAndGetters() {
		OrderRequestDTO dto = new OrderRequestDTO();
		dto.setId(1L);
		dto.setItems(Collections.emptyList());
		dto.setPerson(new PersonDTO());

		assertThat(dto.getId()).isEqualTo(1L);
		assertThat(dto.getItems()).isEmpty();
		assertThat(dto.getPerson()).isNotNull();
	}

	@Test
	void testNoArgsConstructor() {
		OrderRequestDTO dto = new OrderRequestDTO();
		assertThat(dto).isNotNull();
	}

	@Test
	void testSetters() {
		OrderRequestDTO dto = new OrderRequestDTO();
		dto.setId(2L);
		dto.setItems(Collections.emptyList());
		dto.setPerson(new PersonDTO());

		assertThat(dto.getId()).isEqualTo(2L);
		assertThat(dto.getItems()).isEmpty();
		assertThat(dto.getPerson()).isNotNull();
	}

}
