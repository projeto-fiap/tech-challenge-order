package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;
import tech.fiap.project.domain.usecase.order.UpdateOrderUseCase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CheckoutOrderServiceTest {

	@Mock
	private RetrieveOrderUseCase retrieveOrderUseCase;

	@Mock
	private UpdateOrderUseCase updateOrderUseCase;

	@InjectMocks
	private CheckoutOrderService checkoutOrderService;

	private MockedStatic<OrderMapper> orderMapperMockedStatic;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		orderMapperMockedStatic = mockStatic(OrderMapper.class);
	}

	@AfterEach
	void tearDown() {
		orderMapperMockedStatic.close();
	}

	@Test
	void testExecute_OrderFound() {
		Long orderId = 1L;
		Order order = new Order();
		Order updatedOrder = new Order();
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

		when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.of(order));
		when(updateOrderUseCase.setOrderPaid(order)).thenReturn(updatedOrder);
		orderMapperMockedStatic.when(() -> OrderMapper.toDTO(updatedOrder)).thenReturn(orderResponseDTO);

		Optional<OrderResponseDTO> result = checkoutOrderService.execute(orderId);

		assertTrue(result.isPresent());
		verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(orderId);
		verify(updateOrderUseCase, times(1)).setOrderPaid(order);
		orderMapperMockedStatic.verify(() -> OrderMapper.toDTO(updatedOrder), times(1));
	}

	@Test
	void testExecute_OrderNotFound() {
		Long orderId = 1L;

		when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.empty());

		Optional<OrderResponseDTO> result = checkoutOrderService.execute(orderId);

		assertFalse(result.isPresent());
		verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(orderId);
		verify(updateOrderUseCase, never()).setOrderPaid(any(Order.class));
		orderMapperMockedStatic.verify(() -> OrderMapper.toDTO(any(Order.class)), never());
	}

}
