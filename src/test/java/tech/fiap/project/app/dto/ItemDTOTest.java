package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import tech.fiap.project.infra.entity.ItemCategory;
import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ItemDTOTest {

	@Test
	void testItemDTOConstructorAndGetters() {
		ItemDTO dto = new ItemDTO(1L, BigDecimal.valueOf(2), "Hamburguer", "unidade", BigDecimal.valueOf(15.99),
				Collections.emptyList(), ItemCategory.FOOD, "Delicioso hamburguer artesanal",
				"http://example.com/image.jpg");

		assertThat(dto.getId()).isEqualTo(1L);
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
		ItemDTO dto = new ItemDTO();
		assertThat(dto).isNotNull();
	}

	@Test
	void testSetters() {
		ItemDTO dto = new ItemDTO();
		dto.setId(2L);
		dto.setQuantity(BigDecimal.valueOf(3));
		dto.setName("Pizza");
		dto.setUnit("fatia");
		dto.setPrice(BigDecimal.valueOf(20.50));
		dto.setIngredients(Collections.emptyList());
		dto.setCategory(ItemCategory.FOOD);
		dto.setDescription("Pizza de calabresa");
		dto.setImageUrl("http://example.com/pizza.jpg");

		assertThat(dto.getId()).isEqualTo(2L);
		assertThat(dto.getQuantity()).isEqualTo(BigDecimal.valueOf(3));
		assertThat(dto.getName()).isEqualTo("Pizza");
		assertThat(dto.getUnit()).isEqualTo("fatia");
		assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(20.50));
		assertThat(dto.getIngredients()).isEmpty();
		assertThat(dto.getCategory()).isEqualTo(ItemCategory.FOOD);
		assertThat(dto.getDescription()).isEqualTo("Pizza de calabresa");
		assertThat(dto.getImageUrl()).isEqualTo("http://example.com/pizza.jpg");
	}

}