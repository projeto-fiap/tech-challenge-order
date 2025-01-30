package tech.fiap.project.app.service.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.app.adapter.ItemMapper;
import tech.fiap.project.app.dto.ItemDTO;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.domain.usecase.item.RetrieveItemUseCase;
import tech.fiap.project.infra.entity.ItemCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveItemServiceTest {

    @Mock
    private RetrieveItemUseCase retrieveItemUseCase;

    @InjectMocks
    private RetrieveItemService retrieveItemService;

    private Item item;
    private ItemDTO itemDTO;
    private List<Item> itemList;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1L);
        item.setName("Item Teste");
        item.setPrice(BigDecimal.valueOf(10.0));
        item.setQuantity(BigDecimal.valueOf(2));
        item.setUnit("un");
        item.setDescription("Teste de item");
        item.setImageUrl("http://image.url");
        item.setItemCategory(ItemCategory.FOOD);
        item.setIngredients(List.of());

        itemDTO = ItemMapper.toDTO(item);
        itemList = List.of(item);
    }

    @Test
    void testFindAll() {

        when(retrieveItemUseCase.findAll()).thenReturn(itemList);
        List<ItemDTO> result = retrieveItemService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(itemDTO.getName(), result.get(0).getName());
        verify(retrieveItemUseCase, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long itemId = 1L;
        when(retrieveItemUseCase.findById(itemId)).thenReturn(Optional.of(item));
        Optional<ItemDTO> result = retrieveItemService.findById(itemId);
        assertTrue(result.isPresent());
        assertEquals(itemDTO.getName(), result.get().getName());
        verify(retrieveItemUseCase, times(1)).findById(itemId);
    }

    @Test
    void testFindByIdNotFound() {
        Long itemId = 1L;
        when(retrieveItemUseCase.findById(itemId)).thenReturn(Optional.empty());
        Optional<ItemDTO> result = retrieveItemService.findById(itemId);
        assertFalse(result.isPresent());
        verify(retrieveItemUseCase, times(1)).findById(itemId);
    }

    @Test
    void testFindByCategory() {

        String category = "FOOD";
        when(retrieveItemUseCase.findByCategory(ItemCategory.FOOD)).thenReturn(itemList);
        List<ItemDTO> result = retrieveItemService.findByCategory(category);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(itemDTO.getName(), result.get(0).getName());
        verify(retrieveItemUseCase, times(1)).findByCategory(ItemCategory.FOOD);
    }


    @Test
    void testFindByCategoryInvalid() {
        String category = "INVALID_CATEGORY";
        assertThrows(IllegalArgumentException.class, () -> retrieveItemService.findByCategory(category));
    }
}
