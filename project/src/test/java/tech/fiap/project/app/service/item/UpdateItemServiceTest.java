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
import tech.fiap.project.domain.usecase.item.UpdateItemUseCase;
import tech.fiap.project.infra.entity.ItemCategory;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateItemServiceTest {

    @Mock
    private UpdateItemUseCase updateItemUseCase;

    @InjectMocks
    private UpdateItemService updateItemService;

    private ItemDTO itemDTO;
    private Item updatedItem;
    private ItemDTO updatedItemDTO;

    @BeforeEach
    void setUp() {
        itemDTO = new ItemDTO();
        itemDTO.setId(1L);
        itemDTO.setName("Item Teste");
        itemDTO.setPrice(BigDecimal.valueOf(10.0));
        itemDTO.setQuantity(BigDecimal.valueOf(2));
        itemDTO.setUnit("un");
        itemDTO.setDescription("Teste de item");
        itemDTO.setImageUrl("http://image.url");
        itemDTO.setCategory(ItemCategory.valueOf("FOOD"));

        updatedItem = new Item();
        updatedItem.setId(1L);
        updatedItem.setName("Item Atualizado");
        updatedItem.setPrice(BigDecimal.valueOf(15.0));
        updatedItem.setQuantity(BigDecimal.valueOf(3));
        updatedItem.setUnit("un");
        updatedItem.setDescription("Item atualizado");
        updatedItem.setImageUrl("http://image.updated.url");
        updatedItem.setItemCategory(null);
        updatedItem.setIngredients(null);

        updatedItemDTO = ItemMapper.toDTO(updatedItem);
    }

    @Test
    void testUpdateItem() {
        when(updateItemUseCase.execute(anyLong(), any(Item.class))).thenReturn(updatedItem);

        ItemDTO result = updateItemService.updateItem(1L, itemDTO);

        assertNotNull(result);
        assertEquals(updatedItemDTO.getName(), result.getName());
        assertEquals(updatedItemDTO.getPrice(), result.getPrice());
        assertEquals(updatedItemDTO.getQuantity(), result.getQuantity());
        assertEquals(updatedItemDTO.getUnit(), result.getUnit());
        assertEquals(updatedItemDTO.getDescription(), result.getDescription());
        assertEquals(updatedItemDTO.getImageUrl(), result.getImageUrl());
        verify(updateItemUseCase, times(1)).execute(eq(1L), any(Item.class));
    }

    @Test
    void testUpdateItemNotFound() {

        when(updateItemUseCase.execute(anyLong(), any(Item.class))).thenReturn(null);


        ItemDTO result = updateItemService.updateItem(1L, itemDTO);


        assertNull(result);


        verify(updateItemUseCase, times(1)).execute(eq(1L), any(Item.class));
    }

}
