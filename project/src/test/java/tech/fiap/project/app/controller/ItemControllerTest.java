package tech.fiap.project.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tech.fiap.project.app.dto.CreateItemRequestDTO;
import tech.fiap.project.app.dto.ItemDTO;
import tech.fiap.project.app.service.item.CreateItemService;
import tech.fiap.project.app.service.item.DeleteItemService;
import tech.fiap.project.app.service.item.RetrieveItemService;
import tech.fiap.project.app.service.item.UpdateItemService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemControllerTest {

	@InjectMocks
	private ItemController itemController;

	@Mock
	private CreateItemService createItemService;

	@Mock
	private RetrieveItemService retrieveItemService;

	@Mock
	private UpdateItemService updateItemService;

	@Mock
	private DeleteItemService deleteItemService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateItems() {
		List<CreateItemRequestDTO> requestDTOs = Collections.singletonList(new CreateItemRequestDTO());
		List<ItemDTO> responseDTOs = Collections.singletonList(new ItemDTO());
		when(createItemService.createItem(requestDTOs)).thenReturn(responseDTOs);

		ResponseEntity<List<ItemDTO>> response = itemController.createItems(requestDTOs);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(responseDTOs, response.getBody());
	}

	@Test
	void testFindAll() {
		List<ItemDTO> items = Collections.singletonList(new ItemDTO());
		when(retrieveItemService.findAll()).thenReturn(items);

		ResponseEntity<List<ItemDTO>> response = itemController.findAll();

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(items, response.getBody());
	}

	@Test
	void testFindByCategory() {
		String category = "Beverages";
		List<ItemDTO> items = Collections.singletonList(new ItemDTO());
		when(retrieveItemService.findByCategory(category)).thenReturn(items);

		ResponseEntity<List<ItemDTO>> response = itemController.findByCategory(category);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(items, response.getBody());
	}

	@Test
	void testUpdateItem() {
		Long id = 1L;
		ItemDTO itemDTO = new ItemDTO();
		when(updateItemService.updateItem(id, itemDTO)).thenReturn(itemDTO);

		ResponseEntity<ItemDTO> response = itemController.updateItem(id, itemDTO);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(itemDTO, response.getBody());
	}

	@Test
	void testDeleteItem() {
		Long id = 1L;
		doNothing().when(deleteItemService).deleteItem(id);

		ResponseEntity<Void> response = itemController.deleteItem(id);

		assertNotNull(response);
		assertEquals(204, response.getStatusCodeValue());
	}

}
