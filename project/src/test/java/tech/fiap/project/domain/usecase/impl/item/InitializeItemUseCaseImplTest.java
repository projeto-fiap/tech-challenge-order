package tech.fiap.project.domain.usecase.impl.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.infra.exception.ItemNotFound;
import tech.fiap.project.infra.exception.NullIdException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitializeItemUseCaseImplTest {

	@Mock
	private ItemDataProvider itemDataProvider;

	@InjectMocks
	private InitializeItemUseCaseImpl initializeItemUseCase;

	private Order order;

	private Item item1;

	private Item item2;

	@BeforeEach
	void setUp() {
		item1 = createItem(1L, "Item 1");
		item2 = createItem(2L, "Item 2");
		order = new Order();
		order.setItems(List.of(item1, item2));
	}

	private Item createItem(Long id, String name) {
		return new Item(id, name, null, null, null, null, null, null, null);
	}

	@Test
	void execute_ShouldInitializeItemsSuccessfully() {
		mockItemDataProvider(item1, item2);

		initializeItemUseCase.execute(order);

		assertEquals(2, order.getItems().size());
		verifyItemDataProviderCalls();
	}

	@Test
	void execute_ShouldThrowNullIdException_WhenItemIdIsNull() {
		item1.setId(null);
		order.setItems(List.of(item1));

		NullIdException exception = assertThrows(NullIdException.class, () -> initializeItemUseCase.execute(order));
		assertNotNull(exception);
	}

	@Test
	void execute_ShouldThrowItemNotFoundException_WhenItemNotFound() {
		when(itemDataProvider.retrieveById(1L)).thenReturn(Optional.empty());
		order.setItems(List.of(item1));

		ItemNotFound exception = assertThrows(ItemNotFound.class, () -> initializeItemUseCase.execute(order));
		assertNotNull(exception);
	}

	private void mockItemDataProvider(Item... items) {
		for (Item item : items) {
			when(itemDataProvider.retrieveById(item.getId())).thenReturn(Optional.of(item));
		}
	}

	private void verifyItemDataProviderCalls() {
		verify(itemDataProvider, times(1)).retrieveById(1L);
		verify(itemDataProvider, times(1)).retrieveById(2L);
	}

}
