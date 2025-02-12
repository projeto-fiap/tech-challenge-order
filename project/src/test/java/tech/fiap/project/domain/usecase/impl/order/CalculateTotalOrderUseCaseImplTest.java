package tech.fiap.project.domain.usecase.impl.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.fiap.project.domain.entity.Item;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateTotalOrderUseCaseImplTest {

	private CalculateTotalOrderUseCaseImpl calculateTotalOrderUseCase;

	@BeforeEach
	void setUp() {
		calculateTotalOrderUseCase = new CalculateTotalOrderUseCaseImpl();
	}

	@Test
	void execute_ShouldCalculateTotal_WhenItemsAndIngredientsExist() {
		// Given
		Item item1 = new Item();
		item1.setPrice(new BigDecimal("10.00"));
		item1.setQuantity(new BigDecimal("2"));

		Item item2 = new Item();
		item2.setPrice(new BigDecimal("5.00"));
		item2.setQuantity(new BigDecimal("3"));

		Item ingredient1 = new Item();
		ingredient1.setPrice(new BigDecimal("1.00"));
		ingredient1.setQuantity(new BigDecimal("1"));

		item1.setIngredients(Arrays.asList(ingredient1)); // Ingrediente adicionado ao
															// item1

		List<Item> items = Arrays.asList(item1, item2);

		// When
		BigDecimal total = calculateTotalOrderUseCase.execute(items);

		// Then
		BigDecimal expectedTotal = new BigDecimal("36.00"); // (10.00*2 + 1.00*1) +
															// (5.00*3)
		assertEquals(expectedTotal, total);
	}

	@Test
	void execute_ShouldCalculateTotal_WhenNoIngredients() {
		// Given
		Item item1 = new Item();
		item1.setPrice(new BigDecimal("10.00"));
		item1.setQuantity(new BigDecimal("2"));

		Item item2 = new Item();
		item2.setPrice(new BigDecimal("5.00"));
		item2.setQuantity(new BigDecimal("3"));

		List<Item> items = Arrays.asList(item1, item2);

		// When
		BigDecimal total = calculateTotalOrderUseCase.execute(items);

		// Then
		BigDecimal expectedTotal = new BigDecimal("35.00"); // (10.00*2) + (5.00*3)
		assertEquals(expectedTotal, total);
	}

	@Test
	void execute_ShouldReturnZero_WhenNoItems() {
		// Given
		List<Item> items = Arrays.asList();

		// When
		BigDecimal total = calculateTotalOrderUseCase.execute(items);

		// Then
		assertEquals(BigDecimal.ZERO, total);
	}

}
