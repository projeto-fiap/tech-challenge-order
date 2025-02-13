package tech.fiap.project.app.service.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.domain.usecase.item.DeleteItemUseCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteItemServiceTest {

	@Mock
	private DeleteItemUseCase deleteItemUseCase;

	@InjectMocks
	private DeleteItemService deleteItemService;

	private Long itemId;

	@BeforeEach
	void setUp() {
		itemId = 1L;
	}

	@Test
	void testDeleteItem() {
		deleteItemService.deleteItem(itemId);
		verify(deleteItemUseCase, times(1)).execute(itemId);
	}

	@Test
	void testDeleteItemWhenIdIsNull() {

		Long nullItemId = null;

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			deleteItemService.deleteItem(nullItemId);
		});

		assertEquals("ID cannot be null", exception.getMessage());

		verify(deleteItemUseCase, times(0)).execute(nullItemId);
	}

}
