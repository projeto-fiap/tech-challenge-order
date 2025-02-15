package tech.integration.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tech.fiap.project.app.dto.ItemDTO;
import tech.fiap.project.app.service.item.RetrieveItemService;
import tech.fiap.project.domain.usecase.item.RetrieveItemUseCase;
import tech.fiap.project.infra.configuration.Configuration;
import tech.fiap.project.infra.entity.ItemCategory;
import tech.fiap.project.infra.entity.ItemEntity;
import tech.fiap.project.infra.repository.ItemRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = { Configuration.class, ServletWebServerFactoryAutoConfiguration.class })
@ActiveProfiles("integration-test")
public class RetrieveItemServiceIntegrationTest {

	@Autowired
	private RetrieveItemService retrieveItemService;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private RetrieveItemUseCase retrieveItemUseCase;

	@BeforeEach
	void setUp() {
		// Limpa o repositório antes de cada teste
		itemRepository.deleteAll();
	}

	@Test
	void findAll_shouldReturnListOfItems_whenSuccessful() {
		// Arrange
		ItemEntity item1 = new ItemEntity();
		item1.setName("Item 1");
		item1.setDescription("Description of Item 1");
		item1.setPrice(BigDecimal.valueOf(100.0));
		item1.setItemCategory(ItemCategory.FOOD);
		itemRepository.save(item1);

		ItemEntity item2 = new ItemEntity();
		item2.setName("Item 2");
		item2.setDescription("Description of Item 2");
		item2.setPrice(BigDecimal.valueOf(200.0));
		item2.setItemCategory(ItemCategory.FOOD);
		itemRepository.save(item2);

		// Act
		List<ItemDTO> items = retrieveItemService.findAll();

		// Assert
		assertNotNull(items); // Verifica se a lista de itens não é nula
		assertEquals(2, items.size()); // Verifica se a lista contém exatamente 2 itens

		// Verifica o primeiro item
		assertEquals("Item 1", items.get(0).getName());
		assertEquals("Description of Item 1", items.get(0).getDescription());
		assertEquals(100.00, items.get(0).getPrice().doubleValue(), 0.01);

		// Verifica o segundo item
		assertEquals("Item 2", items.get(1).getName());
		assertEquals("Description of Item 2", items.get(1).getDescription());
		assertEquals(200.00, items.get(1).getPrice().doubleValue(), 0.01);

	}

	@Test
	void findByCategory_shouldReturnListOfItems_whenCategoryExists() {
		// Arrange
		ItemEntity item1 = new ItemEntity();
		item1.setName("Item 1");
		item1.setDescription("Description of Item 1");
		item1.setPrice(BigDecimal.valueOf(100.00));
		item1.setItemCategory(ItemCategory.FOOD); // Definindo a categoria como FOOD
		itemRepository.save(item1);

		ItemEntity item2 = new ItemEntity();
		item2.setName("Item 2");
		item2.setDescription("Description of Item 2");
		item2.setPrice(BigDecimal.valueOf(100.0));
		item2.setItemCategory(ItemCategory.FOOD); // Definindo a categoria como FOOD
		itemRepository.save(item2);

		// Act
		List<ItemDTO> foodItems = retrieveItemService.findByCategory("FOOD"); // Buscando
		// por
		// FOOD

		// Assert
		assertNotNull(foodItems);
		assertEquals(2, foodItems.size()); // Esperamos 2 itens da categoria FOOD
		assertEquals("Item 1", foodItems.get(0).getName());
		assertEquals("Description of Item 1", foodItems.get(0).getDescription());
		assertEquals(0, BigDecimal.valueOf(100.0).compareTo(foodItems.get(0).getPrice()));
		assertEquals("Item 2", foodItems.get(1).getName());
		assertEquals("Description of Item 2", foodItems.get(1).getDescription());
		assertEquals(0, BigDecimal.valueOf(100.0).compareTo(foodItems.get(1).getPrice()));
	}

	@Test
	void findByCategory_shouldReturnEmptyList_whenCategoryDoesNotExist() {
		// Arrange
		String nonExistentCategory = "NON_EXISTENT_CATEGORY";

		// Act and Assert
		assertThrows(IllegalArgumentException.class, () -> {
			retrieveItemService.findByCategory(nonExistentCategory);
		});
	}

}
