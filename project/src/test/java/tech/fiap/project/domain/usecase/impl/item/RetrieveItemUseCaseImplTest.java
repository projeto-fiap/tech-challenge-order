package tech.fiap.project.domain.usecase.impl.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.ItemCategory;
import tech.fiap.project.infra.exception.ItemNotFound;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveItemUseCaseImplTest {

	@Mock
	private ItemDataProvider itemDataProvider;

	@InjectMocks
	private RetrieveItemUseCaseImpl retrieveItemUseCase;

	private Item item1;

	private Item item2;

	private ItemCategory category;

	@BeforeEach
	void setUp() {
		item1 = new Item(1L, "Item 1", null, null, null, null, null, null, null);
		item2 = new Item(2L, "Item 2", null, null, null, null, null, null, null);
		category = ItemCategory.FOOD;
	}

	@Test
	void findAll_ShouldReturnItemsSuccessfully() {
		when(itemDataProvider.retrieveAll()).thenReturn(List.of(item1, item2));

		List<Item> items = retrieveItemUseCase.findAll();

		assertNotNull(items);
		assertEquals(2, items.size());
		verify(itemDataProvider, times(1)).retrieveAll();
	}

	@Test
	void findAll_ShouldReturnEmptyList_WhenNoItemsFound() {
		when(itemDataProvider.retrieveAll()).thenReturn(List.of());

		List<Item> items = retrieveItemUseCase.findAll();

		assertNotNull(items);
		assertTrue(items.isEmpty());
		verify(itemDataProvider, times(1)).retrieveAll();
	}

	@Test
	void findById_ShouldReturnItemSuccessfully_WhenItemExists() {
		when(itemDataProvider.retrieveById(1L)).thenReturn(Optional.of(item1));

		Optional<Item> retrievedItem = retrieveItemUseCase.findById(1L);

		assertTrue(retrievedItem.isPresent());
		assertEquals(item1, retrievedItem.get());
		verify(itemDataProvider, times(1)).retrieveById(1L);
	}

	@Test
	void findByCategory_ShouldReturnItemsSuccessfully_WhenItemsExist() {
		when(itemDataProvider.retrieveByCategory(category)).thenReturn(List.of(item1, item2));

		List<Item> items = retrieveItemUseCase.findByCategory(category);

		assertNotNull(items);
		assertEquals(2, items.size());
		verify(itemDataProvider, times(1)).retrieveByCategory(category);
	}

	@Test
	void findByCategory_ShouldReturnEmptyList_WhenNoItemsFound() {
		when(itemDataProvider.retrieveByCategory(category)).thenReturn(List.of());

		List<Item> items = retrieveItemUseCase.findByCategory(category);

		assertNotNull(items);
		assertTrue(items.isEmpty());
		verify(itemDataProvider, times(1)).retrieveByCategory(category);
	}

}
