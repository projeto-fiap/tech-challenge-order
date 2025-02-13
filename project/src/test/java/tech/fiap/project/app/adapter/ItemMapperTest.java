package tech.fiap.project.app.adapter;

import org.junit.jupiter.api.Test;
import tech.fiap.project.app.dto.CreateItemRequestDTO;
import tech.fiap.project.app.dto.ItemDTO;
import tech.fiap.project.app.dto.ItemRequestDTO;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.ItemCategory;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

	@Test
	void testToDTO() {
		Item item = new Item(1L, "Burger", BigDecimal.valueOf(10.99), BigDecimal.valueOf(2), "pcs", ItemCategory.FOOD,
				Collections.emptyList(), "Delicious burger", "image_url");

		ItemDTO dto = ItemMapper.toDTO(item);

		assertNotNull(dto);
		assertEquals(item.getId(), dto.getId());
		assertEquals(item.getName(), dto.getName());
		assertEquals(item.getPrice(), dto.getPrice());
		assertEquals(item.getQuantity(), dto.getQuantity());
		assertEquals(item.getUnit(), dto.getUnit());
		assertEquals(item.getItemCategory(), dto.getCategory());
		assertEquals(item.getDescription(), dto.getDescription());
		assertEquals(item.getImageUrl(), dto.getImageUrl());
	}

	@Test
	void testToDomainFromDTO() {
		ItemDTO dto = new ItemDTO();
		dto.setId(1L);
		dto.setName("Burger");
		dto.setPrice(BigDecimal.valueOf(10.99));
		dto.setQuantity(BigDecimal.valueOf(2));
		dto.setUnit("pcs");
		dto.setCategory(ItemCategory.FOOD);
		dto.setDescription("Delicious burger");
		dto.setImageUrl("image_url");

		Item item = ItemMapper.toDomain(dto);

		assertNotNull(item);
		assertEquals(dto.getId(), item.getId());
		assertEquals(dto.getName(), item.getName());
		assertEquals(dto.getPrice(), item.getPrice());
		assertEquals(dto.getQuantity(), item.getQuantity());
		assertEquals(dto.getUnit(), item.getUnit());
		assertEquals(dto.getCategory(), item.getItemCategory());
		assertEquals(dto.getDescription(), item.getDescription());
		assertEquals(dto.getImageUrl(), item.getImageUrl());
	}

	@Test
	void testToDomainFromCreateItemRequestDTO() {
		CreateItemRequestDTO requestDTO = new CreateItemRequestDTO();
		requestDTO.setName("Burger");
		requestDTO.setPrice(BigDecimal.valueOf(10.99));
		requestDTO.setQuantity(BigDecimal.valueOf(2));
		requestDTO.setUnit("pcs");
		requestDTO.setCategory(ItemCategory.FOOD);
		requestDTO.setDescription("Delicious burger");
		requestDTO.setImageUrl("image_url");

		Item item = ItemMapper.toDomain(requestDTO);

		assertNotNull(item);
		assertNull(item.getId());
		assertEquals(requestDTO.getName(), item.getName());
		assertEquals(requestDTO.getPrice(), item.getPrice());
		assertEquals(requestDTO.getQuantity(), item.getQuantity());
		assertEquals(requestDTO.getUnit(), item.getUnit());
		assertEquals(requestDTO.getCategory(), item.getItemCategory());
		assertEquals(requestDTO.getDescription(), item.getDescription());
		assertEquals(requestDTO.getImageUrl(), item.getImageUrl());
	}

	@Test
	void testToDomainFromItemRequestDTO() {
		ItemRequestDTO requestDTO = new ItemRequestDTO();
		requestDTO.setId(1L);
		requestDTO.setQuantity(BigDecimal.valueOf(2));
		requestDTO.setUnit("pcs");

		Item item = ItemMapper.toDomain(requestDTO);

		assertNotNull(item);
		assertEquals(requestDTO.getId(), item.getId());
		assertEquals(requestDTO.getQuantity(), item.getQuantity());
		assertEquals(requestDTO.getUnit(), item.getUnit());
	}

}