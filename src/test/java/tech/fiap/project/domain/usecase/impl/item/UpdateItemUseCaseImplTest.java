package tech.fiap.project.domain.usecase.impl.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;
import tech.fiap.project.domain.entity.Item;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateItemUseCaseImplTest {

	private ItemDataProvider itemDataProvider;

	private UpdateItemUseCaseImpl updateItemUseCase;

	@BeforeEach
	void setUp() {
		itemDataProvider = mock(ItemDataProvider.class);
		updateItemUseCase = new UpdateItemUseCaseImpl(itemDataProvider);
	}

	@Test
	void execute_ShouldUpdateItem_WhenItemExists() {
		// Given
		Item existingItem = new Item();
		existingItem.setId(1L);
		existingItem.setName("Old Item");
		Item updatedItem = new Item();
		updatedItem.setId(1L);
		updatedItem.setName("Updated Item");

		when(itemDataProvider.retrieveById(1L)).thenReturn(Optional.of(existingItem));
		when(itemDataProvider.saveAll(List.of(updatedItem))).thenReturn(List.of(updatedItem));

		// When
		Item result = updateItemUseCase.execute(1L, updatedItem);

		// Then
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Updated Item", result.getName());
		verify(itemDataProvider, times(1)).retrieveById(1L);
		verify(itemDataProvider, times(1)).saveAll(List.of(updatedItem));
	}

	@Test
	void execute_ShouldThrowItemNotFoundException_WhenItemDoesNotExist() {
		// Given
		Item updatedItem = new Item();
		updatedItem.setId(1L);
		updatedItem.setName("Updated Item");

		when(itemDataProvider.retrieveById(1L)).thenReturn(Optional.empty());

		// When / Then
		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> updateItemUseCase.execute(1L, updatedItem));
		assertEquals("Item not found", exception.getMessage());
		verify(itemDataProvider, times(1)).retrieveById(1L);
		verify(itemDataProvider, times(0)).saveAll(anyList());
	}

}
