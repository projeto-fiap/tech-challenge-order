package tech.fiap.project.infra.dataprovider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.ItemCategory;
import tech.fiap.project.infra.entity.ItemEntity;
import tech.fiap.project.infra.mapper.ItemRepositoryMapper;
import tech.fiap.project.infra.repository.ItemRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemDataProviderImplTest {

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private ItemDataProviderImpl itemDataProvider;

	private Item item;

	private ItemEntity itemEntity;

	@BeforeEach
	void setUp() {
		// Cria um Item usando o construtor padrão e setters
		item = new Item();
		item.setId(1L);
		item.setName("Item 1");
		item.setPrice(new BigDecimal("10.00"));
		item.setQuantity(new BigDecimal("5"));
		item.setUnit("kg");
		item.setItemCategory(ItemCategory.FOOD);
		item.setIngredients(Collections.emptyList());
		item.setDescription("Description 1");
		item.setImageUrl("http://example.com/image.jpg");

		// Cria um ItemEntity usando o construtor padrão e setters
		itemEntity = new ItemEntity();
		itemEntity.setId(1L);
		itemEntity.setName("Item 1");
		itemEntity.setPrice(new BigDecimal("10.00"));
		itemEntity.setQuantity(new BigDecimal("5"));
		itemEntity.setUnit("kg");
		itemEntity.setItemCategory(ItemCategory.FOOD);
		itemEntity.setIngredients(Collections.emptyList());
		itemEntity.setDescription("Description 1");
		itemEntity.setImageUrl("http://example.com/image.jpg");
	}

	@Test
	void testRetrieveAll() {
		// Arrange
		when(itemRepository.findAll()).thenReturn(Collections.singletonList(itemEntity));

		// Act
		List<Item> result = itemDataProvider.retrieveAll();

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(item.getId(), result.get(0).getId());
		verify(itemRepository, times(1)).findAll();
	}

	@Test
	void testRetrieveById() {
		// Arrange
		when(itemRepository.findById(1L)).thenReturn(Optional.of(itemEntity));

		// Act
		Optional<Item> result = itemDataProvider.retrieveById(1L);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(item.getId(), result.get().getId());
		verify(itemRepository, times(1)).findById(1L);
	}

	@Test
	void testRetrieveByIdNotFound() {
		// Arrange
		when(itemRepository.findById(1L)).thenReturn(Optional.empty());

		// Act
		Optional<Item> result = itemDataProvider.retrieveById(1L);

		// Assert
		assertFalse(result.isPresent());
		verify(itemRepository, times(1)).findById(1L);
	}

	@Test
	void testSave() {
		// Arrange
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setId(1L);
		itemEntity.setName("Item 1");
		itemEntity.setPrice(new BigDecimal("10.00"));
		itemEntity.setQuantity(new BigDecimal("5"));
		itemEntity.setUnit("kg");
		itemEntity.setItemCategory(ItemCategory.FOOD);
		itemEntity.setIngredients(Collections.emptyList()); // Lista vazia
		itemEntity.setDescription("Description 1");
		itemEntity.setImageUrl("http://example.com/image.jpg");

		// Usando doReturn() para garantir que o itemEntity seja o mesmo
		doReturn(itemEntity).when(itemRepository).save(any(ItemEntity.class));

		// Act
		Item result = itemDataProvider.save(item);

		// Assert
		assertNotNull(result);
		assertEquals(item.getId(), result.getId());
		verify(itemRepository, times(1)).save(any(ItemEntity.class)); // Usando any() no
																		// verify também
	}

	@Test
	void testSaveAll() {
		// Arrange
		List<Item> items = Collections.singletonList(item); // Lista de Item (domínio)
		List<ItemEntity> itemEntities = Collections.singletonList(itemEntity); // Lista de
																				// ItemEntity
																				// (entidade)

		// Usando doReturn() para garantir que o itemEntities seja o mesmo
		doReturn(itemEntities).when(itemRepository).saveAll(anyList());

		// Act
		List<Item> result = itemDataProvider.saveAll(items);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(item.getId(), result.get(0).getId());
		verify(itemRepository, times(1)).saveAll(anyList()); // Usando anyList() no verify
																// também
	}

	@Test
	void testDeleteById() {
		// Act
		itemDataProvider.deleteById(1L);

		// Assert
		verify(itemRepository, times(1)).deleteById(1L);
	}

	@Test
	void testRetrieveByCategory() {
		// Arrange
		when(itemRepository.findByCategory(ItemCategory.FOOD)).thenReturn(Collections.singletonList(itemEntity));

		// Act
		List<Item> result = itemDataProvider.retrieveByCategory(ItemCategory.FOOD);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(item.getId(), result.get(0).getId());
		verify(itemRepository, times(1)).findByCategory(ItemCategory.FOOD);
	}

}