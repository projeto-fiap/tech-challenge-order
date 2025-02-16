package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;
import tech.fiap.project.domain.usecase.order.UpdateOrderUseCase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoneOrderServiceTest {

	@Mock
	private RetrieveOrderUseCase retrieveOrderUseCase;

	@Mock
	private UpdateOrderUseCase updateOrderUseCase;

	@InjectMocks
	private DoneOrderService doneOrderService;

	private Order order;

	private OrderResponseDTO orderResponseDTO;

	@BeforeEach
	void setUp() {
		// Inicializa um pedido e seu DTO correspondente
		order = new Order();
		order.setId(1L);

		orderResponseDTO = new OrderResponseDTO();
		orderResponseDTO.setId(1L);
	}

	@Test
	void execute_ShouldReturnOrderResponseDTO_WhenOrderExists() {
		try (MockedStatic<OrderMapper> mockedOrderMapper = Mockito.mockStatic(OrderMapper.class)) {
			// Arrange
			when(retrieveOrderUseCase.findByIdWithPayment(1L)).thenReturn(Optional.of(order));
			when(updateOrderUseCase.setOrderDone(order)).thenReturn(order);
			mockedOrderMapper.when(() -> OrderMapper.toDTO(order)).thenReturn(orderResponseDTO);

			// Act
			Optional<OrderResponseDTO> result = doneOrderService.execute(1L);

			// Assert
			assertTrue(result.isPresent());
			assertEquals(orderResponseDTO, result.get());

			verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(1L);
			verify(updateOrderUseCase, times(1)).setOrderDone(order);
			mockedOrderMapper.verify(() -> OrderMapper.toDTO(order), times(1)); // Verificação
																				// do
																				// método
																				// estático
		}
	}

	@Test
	void execute_ShouldReturnEmpty_WhenOrderDoesNotExist() {
		try (MockedStatic<OrderMapper> mockedOrderMapper = Mockito.mockStatic(OrderMapper.class)) {
			// Arrange
			when(retrieveOrderUseCase.findByIdWithPayment(1L)).thenReturn(Optional.empty());

			// Act
			Optional<OrderResponseDTO> result = doneOrderService.execute(1L);

			// Assert
			assertFalse(result.isPresent());

			verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(1L);
			verify(updateOrderUseCase, never()).setOrderDone(any());
			mockedOrderMapper.verify(() -> OrderMapper.toDTO(any(Order.class)), never()); // Especificando
																							// o
																							// tipo
																							// Order
		}
	}

}