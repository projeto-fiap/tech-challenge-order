package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientCategoryTest {

	@Test
	void testIngredientCategoryValues() {
		assertThat(IngredientCategory.values()).containsExactly(IngredientCategory.ADDITIONAL,
				IngredientCategory.NOT_PURCHASED);
	}

	@Test
	void testIngredientCategoryValueOf() {
		assertThat(IngredientCategory.valueOf("ADDITIONAL")).isEqualTo(IngredientCategory.ADDITIONAL);
		assertThat(IngredientCategory.valueOf("NOT_PURCHASED")).isEqualTo(IngredientCategory.NOT_PURCHASED);
	}

}
