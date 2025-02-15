package tech.fiap.project.domain.usecase.impl.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.ItemCategory;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateItemUseCaseImplTest {

	private CreateItemUseCaseImpl createItemUseCase;

	@Mock
	private ItemDataProvider itemDataProvider;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		createItemUseCase = new CreateItemUseCaseImpl(itemDataProvider);
	}

	@Test
	void shouldSaveAllItemsSuccessfully() {
		// Arrange
		Item item1 = new Item(1L, "Item 1", BigDecimal.valueOf(10.0), BigDecimal.valueOf(1), "unit", ItemCategory.FOOD,
				null, "description", "imageUrl");
		Item item2 = new Item(2L, "Item 2", BigDecimal.valueOf(15.0), BigDecimal.valueOf(2), "unit", ItemCategory.FOOD,
				null, "description", "imageUrl");
		List<Item> items = List.of(item1, item2);

		when(itemDataProvider.saveAll(items)).thenReturn(items);

		List<Item> savedItems = createItemUseCase.execute(items);

		assertEquals(items.size(), savedItems.size());
		assertEquals(items, savedItems);
		verify(itemDataProvider, times(1)).saveAll(items);
	}

}
