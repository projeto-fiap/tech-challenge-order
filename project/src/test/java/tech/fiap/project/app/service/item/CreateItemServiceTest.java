package tech.fiap.project.app.service.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.app.adapter.ItemMapper;
import tech.fiap.project.app.dto.CreateItemRequestDTO;
import tech.fiap.project.app.dto.ItemDTO;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.domain.usecase.item.CreateItemUseCase;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateItemServiceTest {

	@Mock
	private CreateItemUseCase createItemUseCase;

	@InjectMocks
	private CreateItemService createItemService;

	private List<CreateItemRequestDTO> itemRequestDTOs;

	private List<Item> itemEntities;

	private List<ItemDTO> itemDTOs;

	@BeforeEach
	void setUp() {
		CreateItemRequestDTO requestDTO = new CreateItemRequestDTO();
		requestDTO.setName("Item Teste");
		requestDTO.setPrice(BigDecimal.valueOf(10.0));
		requestDTO.setQuantity(BigDecimal.valueOf(2));
		requestDTO.setUnit("un");
		requestDTO.setDescription("Teste de item");
		requestDTO.setImageUrl("http://image.url");
		requestDTO.setCategory(null);
		requestDTO.setIngredients(List.of());

		itemRequestDTOs = List.of(requestDTO);

		Item item = new Item();
		item.setId(1L);
		item.setName("Item Teste");
		item.setPrice(BigDecimal.valueOf(10.0));
		item.setQuantity(BigDecimal.valueOf(2));
		item.setUnit("un");
		item.setDescription("Teste de item");
		item.setImageUrl("http://image.url");
		item.setItemCategory(null);
		item.setIngredients(List.of());
		itemEntities = List.of(item);
		itemDTOs = List.of(ItemMapper.toDTO(item));
	}

	@Test
	void testCreateItem() {
		when(createItemUseCase.execute(anyList())).thenReturn(itemEntities);
		List<ItemDTO> result = createItemService.createItem(itemRequestDTOs);
		assertEquals(itemDTOs.size(), result.size());
		assertEquals(itemDTOs.get(0).getName(), result.get(0).getName());
		verify(createItemUseCase, times(1)).execute(anyList());
	}

}
