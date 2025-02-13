package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import tech.fiap.project.infra.entity.ItemCategory;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class CreateItemRequestDTOTest {

	@Test
	void testCreateItemRequestDTO() {
		CreateItemRequestDTO dto = new CreateItemRequestDTO();
		dto.setQuantity(BigDecimal.valueOf(2));
		dto.setName("Hamburguer");
		dto.setUnit("unidade");
		dto.setPrice(BigDecimal.valueOf(15.99));
		dto.setIngredients(Collections.emptyList());
		dto.setCategory(ItemCategory.FOOD);
		dto.setDescription("Delicioso hamburguer artesanal");
		dto.setImageUrl("http://example.com/image.jpg");

		assertThat(dto.getQuantity()).isEqualTo(BigDecimal.valueOf(2));
		assertThat(dto.getName()).isEqualTo("Hamburguer");
		assertThat(dto.getUnit()).isEqualTo("unidade");
		assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(15.99));
		assertThat(dto.getIngredients()).isEmpty();
		assertThat(dto.getCategory()).isEqualTo(ItemCategory.FOOD);
		assertThat(dto.getDescription()).isEqualTo("Delicioso hamburguer artesanal");
		assertThat(dto.getImageUrl()).isEqualTo("http://example.com/image.jpg");
	}

	@Test
	void testNoArgsConstructor() {
		CreateItemRequestDTO dto = new CreateItemRequestDTO();
		assertThat(dto).isNotNull();
	}

}
