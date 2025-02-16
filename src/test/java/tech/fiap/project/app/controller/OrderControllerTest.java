package tech.fiap.project.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tech.fiap.project.app.dto.OrderRequestDTO;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.app.service.order.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private CreateOrderService createOrderService;

	@Mock
	private RetrieveOrderService retrieveOrderService;

	@Mock
	private EndOrderService endOrderService;

	@Mock
	private CheckoutOrderService checkoutOrderService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateOrUpdate() {
		OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		when(createOrderService.execute(orderRequestDTO)).thenReturn(orderResponseDTO);

		ResponseEntity<OrderResponseDTO> response = orderController.createOrUpdate(orderRequestDTO);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderResponseDTO, response.getBody());
	}

	@Test
	void testRetrieveOrders() {
		List<OrderResponseDTO> orders = Collections.singletonList(new OrderResponseDTO());
		when(retrieveOrderService.findAll()).thenReturn(orders);

		ResponseEntity<List<OrderResponseDTO>> response = orderController.retrieveOrders();

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orders, response.getBody());
	}

	@Test
	void testRetrieveOrderByIdFound() {
		Long id = 1L;
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		when(retrieveOrderService.findById(id)).thenReturn(Optional.of(orderResponseDTO));

		ResponseEntity<OrderResponseDTO> response = orderController.retrieveOrderById(id);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderResponseDTO, response.getBody());
	}

	@Test
	void testRetrieveOrderByIdNotFound() {
		Long id = 1L;
		when(retrieveOrderService.findById(id)).thenReturn(Optional.empty());

		ResponseEntity<OrderResponseDTO> response = orderController.retrieveOrderById(id);

		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void testOngoingOrders() {
		List<OrderResponseDTO> orders = Collections.singletonList(new OrderResponseDTO());
		when(retrieveOrderService.findOngoingAll()).thenReturn(orders);

		ResponseEntity<List<OrderResponseDTO>> response = orderController.ongoingOrders();

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orders, response.getBody());
	}

}