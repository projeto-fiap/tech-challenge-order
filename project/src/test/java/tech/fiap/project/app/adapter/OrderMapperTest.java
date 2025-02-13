package tech.fiap.project.app.adapter;

import org.junit.jupiter.api.Test;
import tech.fiap.project.app.dto.*;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

	@Test
	void testToDTO() {
		Order order = createOrder();
		OrderResponseDTO dto = OrderMapper.toDTO(order);

		assertNotNull(dto);
		assertEquals(order.getId(), dto.getId());
		assertEquals(order.getStatus(), dto.getStatus());
		assertEquals(order.getCreatedDate(), dto.getCreatedDate());
		assertEquals(order.getUpdatedDate(), dto.getUpdatedDate());
		assertEquals(order.getTotalPrice(), dto.getTotalPrice());
		assertEquals(order.getPerson(), dto.getPerson());
		assertEquals(order.getAwaitingTime(), dto.getAwaitingTime());
		assertEquals(order.getItems().size(), dto.getItems().size());
		assertEquals(order.getPayments().size(), dto.getPayment().size());
	}

	@Test
	void testToDTOWithKitchen() {
		Order order = createOrder();
		KitchenDTO kitchenDTO = new KitchenDTO();
		kitchenDTO.setOrderId(order.getId());

		OrderResponseDTO dto = OrderMapper.toDTO(order, Optional.of(kitchenDTO));
		assertNotNull(dto);
		assertEquals(kitchenDTO, dto.getKitchenQueue());
	}

	@Test
	void testToDomainFromResponseDTO() {
		OrderResponseDTO dto = createOrderResponseDTO();
		Order order = OrderMapper.toDomain(dto);

		assertNotNull(order);
		assertEquals(dto.getId(), order.getId());
		assertEquals(dto.getStatus(), order.getStatus());
		assertEquals(dto.getCreatedDate(), order.getCreatedDate());
		assertEquals(dto.getUpdatedDate(), order.getUpdatedDate());
		assertEquals(dto.getTotalPrice(), order.getTotalPrice());
		assertEquals(dto.getPerson(), order.getPerson());
		assertEquals(dto.getAwaitingTime(), order.getAwaitingTime());
		assertEquals(dto.getItems().size(), order.getItems().size());
		assertEquals(dto.getPayment().size(), order.getPayments().size());
	}

	private Order createOrder() {
		Order order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.PENDING);
		order.setCreatedDate(LocalDateTime.now());
		order.setUpdatedDate(LocalDateTime.now());
		order.setTotalPrice(BigDecimal.valueOf(50.00));
		order.setPerson(new PersonDTO());
		order.setAwaitingTime(null);
		order.setItems(Collections.emptyList());
		order.setPayments(Collections.emptyList());
		return order;
	}

	private OrderResponseDTO createOrderResponseDTO() {
		OrderResponseDTO dto = new OrderResponseDTO();
		dto.setId(1L);
		dto.setStatus(OrderStatus.PENDING);
		dto.setCreatedDate(LocalDateTime.now());
		dto.setUpdatedDate(LocalDateTime.now());
		dto.setTotalPrice(BigDecimal.valueOf(50.00));
		dto.setPerson(new PersonDTO());
		dto.setAwaitingTime(null);
		dto.setItems(Collections.emptyList());
		dto.setPayment(Collections.emptyList());
		return dto;
	}

}
