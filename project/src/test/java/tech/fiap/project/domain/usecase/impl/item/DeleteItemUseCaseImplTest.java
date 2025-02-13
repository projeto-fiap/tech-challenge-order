package tech.fiap.project.domain.usecase.impl.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;

import static org.mockito.Mockito.*;

class DeleteItemUseCaseImplTest {

	private ItemDataProvider itemDataProvider;

	private DeleteItemUseCaseImpl deleteItemUseCase;

	@BeforeEach
	void setUp() {
		itemDataProvider = mock(ItemDataProvider.class);
		deleteItemUseCase = new DeleteItemUseCaseImpl(itemDataProvider);
	}

	@Test
	void execute_ShouldCallDeleteById() {
		Long itemId = 1L;

		deleteItemUseCase.execute(itemId);

		verify(itemDataProvider, times(1)).deleteById(itemId);
	}

}