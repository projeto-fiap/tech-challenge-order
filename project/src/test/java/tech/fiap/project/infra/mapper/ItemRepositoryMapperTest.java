package tech.fiap.project.infra.mapper;

import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.ItemCategory;
import tech.fiap.project.infra.entity.ItemEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryMapperTest {

	@Test
	void testToEntity() {
		// Arrange
		Item item = new Item(1L, "Sandwich", BigDecimal.valueOf(5.99), BigDecimal.valueOf(10), "unit",
				ItemCategory.FOOD, List.of(), "Delicious sandwich", "imageUrl");

		// Act
		ItemEntity itemEntity = ItemRepositoryMapper.toEntity(item);

		// Assert
		assertNotNull(itemEntity);
		assertEquals(item.getId(), itemEntity.getId());
		assertEquals(item.getName(), itemEntity.getName());
		assertEquals(item.getPrice(), itemEntity.getPrice());
		assertEquals(item.getQuantity(), itemEntity.getQuantity());
		assertEquals(item.getUnit(), itemEntity.getUnit());
		assertEquals(item.getItemCategory(), itemEntity.getItemCategory());
		assertEquals(item.getDescription(), itemEntity.getDescription());
		assertEquals(item.getImageUrl(), itemEntity.getImageUrl());
		assertTrue(itemEntity.getIngredients().isEmpty()); // Ingredients should be empty
	}

	@Test
	void testToEntityWithNullItem() {
		// Act
		ItemEntity itemEntity = ItemRepositoryMapper.toEntity(null);

		// Assert
		assertNull(itemEntity); // Should return null
	}

	@Test
	void testToDomain() {
		// Arrange
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setId(1L);
		itemEntity.setName("Sandwich");
		itemEntity.setPrice(BigDecimal.valueOf(5.99));
		itemEntity.setQuantity(BigDecimal.valueOf(10));
		itemEntity.setUnit("unit");
		itemEntity.setItemCategory(ItemCategory.FOOD);
		itemEntity.setIngredients(List.of()); // Empty list for ingredients
		itemEntity.setDescription("Delicious sandwich");
		itemEntity.setImageUrl("imageUrl");

		// Act
		Item item = ItemRepositoryMapper.toDomain(itemEntity);

		// Assert
		assertNotNull(item);
		assertEquals(itemEntity.getId(), item.getId());
		assertEquals(itemEntity.getName(), item.getName());
		assertEquals(itemEntity.getPrice(), item.getPrice());
		assertEquals(itemEntity.getQuantity(), item.getQuantity());
		assertEquals(itemEntity.getUnit(), item.getUnit());
		assertEquals(itemEntity.getItemCategory(), item.getItemCategory());
		assertEquals(itemEntity.getDescription(), item.getDescription());
		assertEquals(itemEntity.getImageUrl(), item.getImageUrl());
		assertTrue(item.getIngredients().isEmpty()); // Ingredients should be empty
	}

	@Test
	void testToDomainWithNullItemEntity() {
		// Act
		Item item = ItemRepositoryMapper.toDomain(null);

		// Assert
		assertNull(item); // Should return null
	}

	@Test
	void testToDomainWithIngredients() {
		// Arrange
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setId(1L);
		itemEntity.setName("Sandwich");
		itemEntity.setPrice(BigDecimal.valueOf(5.99));
		itemEntity.setQuantity(BigDecimal.valueOf(10));
		itemEntity.setUnit("unit");
		itemEntity.setItemCategory(ItemCategory.FOOD);
		itemEntity.setIngredients(List.of(new ItemEntity())); // Adding one ingredient
		itemEntity.setDescription("Delicious sandwich");
		itemEntity.setImageUrl("imageUrl");

		// Act
		Item item = ItemRepositoryMapper.toDomain(itemEntity);

		// Assert
		assertNotNull(item);
		assertEquals(1, item.getIngredients().size()); // Ingredients should have 1
														// element
	}

	@Test
	void testToDomainWithNullIngredients() {
		// Arrange
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setId(1L);
		itemEntity.setName("Sandwich");
		itemEntity.setPrice(BigDecimal.valueOf(5.99));
		itemEntity.setQuantity(BigDecimal.valueOf(10));
		;
		itemEntity.setUnit("unit");
		itemEntity.setItemCategory(ItemCategory.FOOD);
		itemEntity.setIngredients(null); // Null ingredients
		itemEntity.setDescription("Delicious sandwich");
		itemEntity.setImageUrl("imageUrl");

		// Act
		Item item = ItemRepositoryMapper.toDomain(itemEntity);

		// Assert
		assertNotNull(item);
		assertTrue(item.getIngredients().isEmpty()); // Ingredients should be an empty
														// list
	}

}
