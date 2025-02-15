package tech.features.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.fiap.project.app.dto.ItemDTO;
import tech.fiap.project.app.service.item.RetrieveItemService;
import tech.fiap.project.infra.entity.ItemEntity;
import tech.fiap.project.infra.entity.ItemCategory;
import tech.fiap.project.infra.repository.ItemRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RetrieveItemFlow {

	@Autowired
	private RetrieveItemService retrieveItemService;

	@Autowired
	private ItemRepository itemRepository;

	private List<ItemDTO> retrievedItems;

	@Given("a list of valid items exists")
	public void aListOfValidItemsExists() {
		// Cria e salva itens no banco de dados para o teste
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
	}

	@When("the items are retrieved")
	public void theItemsAreRetrieved() {
		// Recupera a lista de itens
		retrievedItems = retrieveItemService.findAll();
	}

	@Then("the items should be returned with their details")
	public void theItemsShouldBeReturnedWithTheirDetails() {
		// Verifica se a lista de itens foi retornada corretamente
		assertNotNull(retrievedItems, "A lista de itens não deve ser nula");
		assertEquals(2, retrievedItems.size(), "A lista deve conter 2 itens");

		// Verifica os detalhes do primeiro item
		ItemDTO firstItem = retrievedItems.get(0);
		assertEquals("Item 1", firstItem.getName(), "O nome do primeiro item está incorreto");
		assertEquals("Description of Item 1", firstItem.getDescription(),
				"A descrição do primeiro item está incorreta");
		assertEquals(0, BigDecimal.valueOf(100.0).compareTo(firstItem.getPrice()),
				"O preço do primeiro item está incorreto");

		// Verifica os detalhes do segundo item
		ItemDTO secondItem = retrievedItems.get(1);
		assertEquals("Item 2", secondItem.getName(), "O nome do segundo item está incorreto");
		assertEquals("Description of Item 2", secondItem.getDescription(),
				"A descrição do segundo item está incorreta");
		assertEquals(0, BigDecimal.valueOf(200.0).compareTo(secondItem.getPrice()),
				"O preço do segundo item está incorreto");
	}

}