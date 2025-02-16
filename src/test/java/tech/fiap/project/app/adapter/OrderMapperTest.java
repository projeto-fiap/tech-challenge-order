package tech.fiap.project.app.adapter;

import org.junit.jupiter.api.Test;
import tech.fiap.project.app.dto.*;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

	@Test
	void toDTO_ShouldConvertListOfOrdersToListOfOrderResponseDTO() {
		// Arrange
		Order order1 = new Order();
		order1.setId(1L);
		order1.setStatus(OrderStatus.PENDING);
		order1.setItems(new ArrayList<>()); // Garante que a lista de itens não será null
		order1.setPayments(new ArrayList<>()); // Garante que a lista de pagamentos não
												// será null

		Order order2 = new Order();
		order2.setId(2L);
		order2.setStatus(OrderStatus.FINISHED);
		order2.setItems(new ArrayList<>()); // Garante que a lista de itens não será null
		order2.setPayments(new ArrayList<>()); // Garante que a lista de pagamentos não
												// será null

		List<Order> orders = List.of(order1, order2);

		// Act
		List<OrderResponseDTO> result = OrderMapper.toDTO(orders);

		// Assert
		assertEquals(2, result.size());
		assertEquals(1L, result.get(0).getId());
		assertEquals(OrderStatus.PENDING, result.get(0).getStatus());
		assertEquals(2L, result.get(1).getId());
		assertEquals(OrderStatus.FINISHED, result.get(1).getStatus());
	}

	@Test
	void toDTO_ShouldConvertListOfOrdersToListOfOrderResponseDTO_WithKitchenDTO() {
		// Arrange
		Order order1 = new Order();
		order1.setId(1L);
		order1.setStatus(OrderStatus.PENDING);
		order1.setItems(new ArrayList<>()); // Garante que a lista de itens não será null
		order1.setPayments(new ArrayList<>()); // Garante que a lista de pagamentos não
												// será null

		Order order2 = new Order();
		order2.setId(2L);
		order2.setStatus(OrderStatus.FINISHED);
		order2.setItems(new ArrayList<>()); // Garante que a lista de itens não será null
		order2.setPayments(new ArrayList<>()); // Garante que a lista de pagamentos não
												// será null

		List<Order> orders = List.of(order1, order2);

		KitchenDTO kitchen1 = new KitchenDTO();
		kitchen1.setOrderId(1L);

		KitchenDTO kitchen2 = new KitchenDTO();
		kitchen2.setOrderId(2L);

		List<KitchenDTO> kitchens = List.of(kitchen1, kitchen2);

		// Act
		List<OrderResponseDTO> result = OrderMapper.toDTO(orders, kitchens);

		// Assert
		assertEquals(2, result.size());
		assertEquals(1L, result.get(0).getId());
		assertEquals(OrderStatus.PENDING, result.get(0).getStatus());
		assertEquals(kitchen1, result.get(0).getKitchenQueue());

		assertEquals(2L, result.get(1).getId());
		assertEquals(OrderStatus.FINISHED, result.get(1).getStatus());
		assertEquals(kitchen2, result.get(1).getKitchenQueue());
	}

	@Test
	void toDTO_ShouldConvertOrderToOrderResponseDTO_WithOptionalKitchenDTO() {
		// Arrange
		Order order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.PENDING);
		order.setCreatedDate(LocalDateTime.now());
		order.setUpdatedDate(LocalDateTime.now());
		order.setItems(List.of()); // Inicializa a lista de itens como vazia
		order.setPayments(List.of()); // Inicializa a lista de pagamentos como vazia

		KitchenDTO kitchen = new KitchenDTO();
		kitchen.setOrderId(1L);

		// Act
		OrderResponseDTO result = OrderMapper.toDTO(order, Optional.of(kitchen));

		// Assert
		assertEquals(1L, result.getId());
		assertEquals(OrderStatus.PENDING, result.getStatus());
		assertEquals(kitchen, result.getKitchenQueue());
		assertNotNull(result.getItems()); // Verifica se a lista de itens não é nula
		assertTrue(result.getItems().isEmpty()); // Verifica se a lista de itens está
													// vazia
		assertNotNull(result.getPayment()); // Verifica se a lista de pagamentos não é
											// nula
		assertTrue(result.getPayment().isEmpty()); // Verifica se a lista de pagamentos
													// está vazia
	}

	@Test
	void toDTO_ShouldConvertOrderToOrderResponseDTO_WithoutKitchenDTO() {
		// Arrange
		Order order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.PENDING);
		order.setCreatedDate(LocalDateTime.now());
		order.setUpdatedDate(LocalDateTime.now());
		order.setItems(List.of()); // Inicializa a lista de itens como vazia
		order.setPayments(List.of()); // Inicializa a lista de pagamentos como vazia

		// Act
		OrderResponseDTO result = OrderMapper.toDTO(order);

		// Assert
		assertEquals(1L, result.getId());
		assertEquals(OrderStatus.PENDING, result.getStatus());
		assertNull(result.getKitchenQueue());
		assertNotNull(result.getItems()); // Verifica se a lista de itens não é nula
		assertTrue(result.getItems().isEmpty()); // Verifica se a lista de itens está
													// vazia
		assertNotNull(result.getPayment()); // Verifica se a lista de pagamentos não é
											// nula
		assertTrue(result.getPayment().isEmpty()); // Verifica se a lista de pagamentos
													// está vazia
	}

	@Test
	void toResponse_ShouldConvertOrderToOrderResponseDTO() {
		// Arrange
		Order order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.PENDING);
		order.setCreatedDate(LocalDateTime.now());
		order.setUpdatedDate(LocalDateTime.now());
		order.setItems(new ArrayList<>()); // Garante que a lista de itens não será null
		order.setPayments(new ArrayList<>()); // Garante que a lista de pagamentos não
												// será null

		// Act
		OrderResponseDTO result = OrderMapper.toResponse(order);

		// Assert
		assertEquals(1L, result.getId());
		assertEquals(OrderStatus.PENDING, result.getStatus());
		assertNull(result.getKitchenQueue());
		assertNotNull(result.getItems()); // Verifica se a lista de itens não é nula
		assertTrue(result.getItems().isEmpty()); // Verifica se a lista de itens está
													// vazia
		assertNotNull(result.getPayment()); // Verifica se a lista de pagamentos não é
											// nula
		assertTrue(result.getPayment().isEmpty()); // Verifica se a lista de pagamentos
													// está vazia
	}

	@Test
	void toDomain_ShouldConvertOrderResponseDTOToOrder() {
		// Arrange
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		orderResponseDTO.setId(1L);
		orderResponseDTO.setStatus(OrderStatus.PENDING);
		orderResponseDTO.setCreatedDate(LocalDateTime.now());
		orderResponseDTO.setUpdatedDate(LocalDateTime.now());

		// Act
		Order result = OrderMapper.toDomain(orderResponseDTO);

		// Assert
		assertEquals(1L, result.getId());
		assertEquals(OrderStatus.PENDING, result.getStatus());
	}

	@Test
	void toDomain_ShouldConvertOrderRequestDTOToOrder() {
		// Arrange
		OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
		orderRequestDTO.setId(1L);
		orderRequestDTO.setPerson(new PersonDTO());

		// Act
		Order result = OrderMapper.toDomain(orderRequestDTO);

		// Assert
		assertEquals(1L, result.getId());
		assertNotNull(result.getCreatedDate());
		assertNull(result.getStatus());
	}

}