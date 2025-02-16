package tech.fiap.project.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tech.fiap.project.app.dto.OrderRequestDTO;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.app.service.order.*;
import tech.fiap.project.domain.entity.OrderStatus;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

	@Mock
	private CreateOrderService createOrderService;

	@Mock
	private RetrieveOrderService retrieveOrderService;

	@Mock
	private EndOrderService endOrderService;

	@Mock
	private CheckoutOrderService checkoutOrderService;

	@Mock
	private DoneOrderService doneOrderService;

	@InjectMocks
	private OrderController orderController;

	private OrderResponseDTO orderResponseDTO;

	@BeforeEach
	void setUp() {
		orderResponseDTO = new OrderResponseDTO();
		orderResponseDTO.setId(1L);
		orderResponseDTO.setStatus(OrderStatus.PENDING);
	}

	@Test
	void createOrUpdate_ShouldReturnCreatedOrder() {
		// Arrange
		OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
		when(createOrderService.execute(orderRequestDTO)).thenReturn(orderResponseDTO);

		// Act
		ResponseEntity<OrderResponseDTO> response = orderController.createOrUpdate(orderRequestDTO);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderResponseDTO, response.getBody());

		verify(createOrderService, times(1)).execute(orderRequestDTO);
	}

	@Test
	void retrieveOrders_ShouldReturnListOfOrders() {
		// Arrange
		List<OrderResponseDTO> orders = Collections.singletonList(orderResponseDTO);
		when(retrieveOrderService.findAll()).thenReturn(orders);

		// Act
		ResponseEntity<List<OrderResponseDTO>> response = orderController.retrieveOrders();

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orders, response.getBody());

		verify(retrieveOrderService, times(1)).findAll();
	}

	@Test
	void retrieveOrderById_ShouldReturnOrder_WhenOrderExists() {
		// Arrange
		when(retrieveOrderService.findById(1L)).thenReturn(Optional.of(orderResponseDTO));

		// Act
		ResponseEntity<OrderResponseDTO> response = orderController.retrieveOrderById(1L);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderResponseDTO, response.getBody());

		verify(retrieveOrderService, times(1)).findById(1L);
	}

	@Test
	void retrieveOrderById_ShouldReturnNotFound_WhenOrderDoesNotExist() {
		// Arrange
		when(retrieveOrderService.findById(1L)).thenReturn(Optional.empty());

		// Act
		ResponseEntity<OrderResponseDTO> response = orderController.retrieveOrderById(1L);

		// Assert
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());

		verify(retrieveOrderService, times(1)).findById(1L);
	}

	@Test
	void endOrder_ShouldReturnQRCodeImage() {
		// Arrange
		BufferedImage qrCodeImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		when(endOrderService.execute(1L)).thenReturn(qrCodeImage);

		// Act
		ResponseEntity<BufferedImage> response = orderController.endOrder(1L);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(qrCodeImage, response.getBody());

		verify(endOrderService, times(1)).execute(1L);
	}

	@Test
	void checkout_ShouldReturnOrder_WhenCheckoutIsSuccessful() {
		// Arrange
		when(checkoutOrderService.execute(1L)).thenReturn(Optional.of(orderResponseDTO));

		// Act
		ResponseEntity<OrderResponseDTO> response = orderController.checkout(1L);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderResponseDTO, response.getBody());

		verify(checkoutOrderService, times(1)).execute(1L);
	}

	@Test
	void checkout_ShouldReturnNotFound_WhenOrderDoesNotExist() {
		// Arrange
		when(checkoutOrderService.execute(1L)).thenReturn(Optional.empty());

		// Act
		ResponseEntity<OrderResponseDTO> response = orderController.checkout(1L);

		// Assert
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());

		verify(checkoutOrderService, times(1)).execute(1L);
	}

	@Test
	void ongoingOrders_ShouldReturnListOfOngoingOrders() {
		// Arrange
		List<OrderResponseDTO> orders = Collections.singletonList(orderResponseDTO);
		when(retrieveOrderService.findOngoingAll()).thenReturn(orders);

		// Act
		ResponseEntity<List<OrderResponseDTO>> response = orderController.ongoingOrders();

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orders, response.getBody());

		verify(retrieveOrderService, times(1)).findOngoingAll();
	}

	@Test
	void done_ShouldReturnOrder_WhenOrderIsMarkedAsDone() {
		// Arrange
		when(doneOrderService.execute(1L)).thenReturn(Optional.of(orderResponseDTO));

		// Act
		ResponseEntity<OrderResponseDTO> response = orderController.done(1L);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(orderResponseDTO, response.getBody());

		verify(doneOrderService, times(1)).execute(1L);
	}

	@Test
	void done_ShouldReturnNotFound_WhenOrderDoesNotExist() {
		// Arrange
		when(doneOrderService.execute(1L)).thenReturn(Optional.empty());

		// Act
		ResponseEntity<OrderResponseDTO> response = orderController.done(1L);

		// Assert
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());

		verify(doneOrderService, times(1)).execute(1L);
	}

}