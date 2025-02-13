package tech.fiap.project.domain.usecase.impl.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.order.CreateOrUpdateOrderUseCase;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;
import tech.fiap.project.infra.exception.OrderNotFound;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Garante que o Mockito inicialize os mocks
class DeliverOrderUseCaseImplTest {

	@Mock
	private CreateOrUpdateOrderUseCase createOrUpdateOrderUsecase;

	@Mock
	private RetrieveOrderUseCase retrieveOrderUseCase;

	@InjectMocks
	private DeliverOrderUseCaseImpl deliverOrderUseCase;

	@Test
	void execute_ShouldDeliverOrder_WhenOrderExistsAndIsNotFinished() {
		Long orderId = 1L;
		Order order = new Order();
		order.setId(orderId);
		order.setStatus(OrderStatus.PENDING); // Status inicial

		when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.of(order));
		when(createOrUpdateOrderUsecase.execute(order)).thenReturn(order);

		Order deliveredOrder = deliverOrderUseCase.execute(orderId);

		assertEquals(OrderStatus.FINISHED, deliveredOrder.getStatus());
		verify(createOrUpdateOrderUsecase, times(1)).execute(order);
	}

	@Test
	void execute_ShouldThrowOrderNotFound_WhenOrderDoesNotExist() {
		Long orderId = 1L;

		when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.empty());

		OrderNotFound exception = assertThrows(OrderNotFound.class, () -> {
			deliverOrderUseCase.execute(orderId);
		});

		String expectedMessage = "Pedido com o identificador [1] não foi encontrado";
		assertTrue(exception.getMessage().contains(expectedMessage)); // Verifica se a
																		// mensagem
																		// esperada está
																		// contida
		assertTrue(exception.getMessage().contains(orderId.toString()));
	}

	@Test
	void execute_ShouldThrowOrderNotFound_WhenOrderIsFinished() {
		Long orderId = 1L;
		Order order = new Order();
		order.setId(orderId);
		order.setStatus(OrderStatus.FINISHED);

		when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.of(order));

		OrderNotFound exception = assertThrows(OrderNotFound.class, () -> {
			deliverOrderUseCase.execute(orderId);
		});

		String expectedMessage = "Pedido com o identificador [1] não foi encontrado";
		assertTrue(exception.getMessage().contains(expectedMessage)); // Verifica se a
																		// mensagem
																		// esperada está
																		// contida
		assertTrue(exception.getMessage().contains(orderId.toString()));
	}

}
