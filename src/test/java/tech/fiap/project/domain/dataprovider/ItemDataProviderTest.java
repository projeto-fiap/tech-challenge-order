package tech.fiap.project.domain.dataprovider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.ItemCategory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ItemDataProviderTest {

	@Mock
	private ItemDataProvider itemDataProvider;

	@InjectMocks
	private ItemDataProviderTest itemDataProviderTest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRetrieveAll() {
		when(itemDataProvider.retrieveAll()).thenReturn(Collections.emptyList());
		List<Item> items = itemDataProvider.retrieveAll();
		assertThat(items).isEmpty();
		verify(itemDataProvider, times(1)).retrieveAll();
	}

	@Test
	void testRetrieveById() {
		Long id = 1L;
		Item item = new Item();
		when(itemDataProvider.retrieveById(id)).thenReturn(Optional.of(item));
		Optional<Item> result = itemDataProvider.retrieveById(id);
		assertThat(result).isPresent();
		verify(itemDataProvider, times(1)).retrieveById(id);
	}

	@Test
	void testSave() {
		Item item = new Item();
		when(itemDataProvider.save(item)).thenReturn(item);
		Item result = itemDataProvider.save(item);
		assertThat(result).isNotNull();
		verify(itemDataProvider, times(1)).save(item);
	}

	@Test
	void testSaveAll() {
		List<Item> items = Collections.singletonList(new Item());
		when(itemDataProvider.saveAll(items)).thenReturn(items);
		List<Item> result = itemDataProvider.saveAll(items);
		assertThat(result).hasSize(1);
		verify(itemDataProvider, times(1)).saveAll(items);
	}

	@Test
	void testDeleteById() {
		Long id = 1L;
		doNothing().when(itemDataProvider).deleteById(id);
		itemDataProvider.deleteById(id);
		verify(itemDataProvider, times(1)).deleteById(id);
	}

	@Test
	void testRetrieveByCategory() {
		ItemCategory category = ItemCategory.FOOD;
		when(itemDataProvider.retrieveByCategory(category)).thenReturn(Collections.emptyList());
		List<Item> result = itemDataProvider.retrieveByCategory(category);
		assertThat(result).isEmpty();
		verify(itemDataProvider, times(1)).retrieveByCategory(category);
	}

}
