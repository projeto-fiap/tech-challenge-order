package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientItemDTOTest {

	@Test
	void testIngredientItemDTOConstructorAndGetters() {
		IngredientItemDTO dto = new IngredientItemDTO(1L, "Tomate", "Tomate fresco", "http://example.com/tomate.jpg",
				BigDecimal.valueOf(2.50), "BRL", IngredientCategory.ADDITIONAL, BigDecimal.valueOf(1));

		assertThat(dto.getId()).isEqualTo(1L);
		assertThat(dto.getName()).isEqualTo("Tomate");
		assertThat(dto.getDescription()).isEqualTo("Tomate fresco");
		assertThat(dto.getImageUrl()).isEqualTo("http://example.com/tomate.jpg");
		assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(2.50));
		assertThat(dto.getCurrency()).isEqualTo("BRL");
		assertThat(dto.getCategory()).isEqualTo(IngredientCategory.ADDITIONAL);
		assertThat(dto.getQuantity()).isEqualTo(BigDecimal.valueOf(1));
	}

	@Test
	void testNoArgsConstructor() {
		IngredientItemDTO dto = new IngredientItemDTO();
		assertThat(dto).isNotNull();
	}

	@Test
	void testSetters() {
		IngredientItemDTO dto = new IngredientItemDTO();
		dto.setId(2L);
		dto.setName("Queijo");
		dto.setDescription("Queijo cheddar");
		dto.setImageUrl("http://example.com/queijo.jpg");
		dto.setPrice(BigDecimal.valueOf(3.00));
		dto.setCurrency("USD");
		dto.setCategory(IngredientCategory.NOT_PURCHASED);
		dto.setQuantity(BigDecimal.valueOf(2));

		assertThat(dto.getId()).isEqualTo(2L);
		assertThat(dto.getName()).isEqualTo("Queijo");
		assertThat(dto.getDescription()).isEqualTo("Queijo cheddar");
		assertThat(dto.getImageUrl()).isEqualTo("http://example.com/queijo.jpg");
		assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(3.00));
		assertThat(dto.getCurrency()).isEqualTo("USD");
		assertThat(dto.getCategory()).isEqualTo(IngredientCategory.NOT_PURCHASED);
		assertThat(dto.getQuantity()).isEqualTo(BigDecimal.valueOf(2));
	}

}