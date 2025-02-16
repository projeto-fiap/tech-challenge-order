package tech.fiap.project.infra.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.infra.entity.ItemCategory;
import tech.fiap.project.infra.entity.ItemEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryTest {

	@Mock
	private ItemRepository itemRepository;

	@BeforeEach
	void setUp() {
		ItemEntity item1 = new ItemEntity();
		item1.setName("Hamburger");
		item1.setPrice(BigDecimal.valueOf(10.99));
		item1.setItemCategory(ItemCategory.FOOD);

		lenient().when(itemRepository.findByCategory(ItemCategory.FOOD)).thenReturn(List.of(item1));
		lenient().when(itemRepository.findByCategory(ItemCategory.DESSERT)).thenReturn(Collections.emptyList());
	}

	@Test
	void shouldFindItemsByCategory() {
		List<ItemEntity> foodItems = itemRepository.findByCategory(ItemCategory.FOOD);

		assertThat(foodItems).isNotEmpty();
		assertThat(foodItems).hasSize(1);
		assertThat(foodItems.get(0).getName()).isEqualTo("Hamburger");
		verify(itemRepository, times(1)).findByCategory(ItemCategory.FOOD);
	}

	@Test
	void shouldReturnEmptyListWhenCategoryNotFound() {
		List<ItemEntity> dessertItems = itemRepository.findByCategory(ItemCategory.DESSERT);

		assertThat(dessertItems).isEmpty();
		verify(itemRepository, times(1)).findByCategory(ItemCategory.DESSERT);
	}

}