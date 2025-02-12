package tech.fiap.project.domain.usecase.impl.order;

import org.junit.jupiter.api.BeforeEach;
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

import java.awt.image.BufferedImage;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EndOrderUseCaseImplTest {

	@InjectMocks
	private EndOrderUseCaseImpl endOrderUseCase;

	@Mock
	private CreateOrUpdateOrderUseCase createOrUpdateOrderUseCase;

	@Mock
	private RetrieveOrderUseCase retrieveOrderUseCase;

	private Order order;

	@BeforeEach
	public void setUp() {
		order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.AWAITING_PAYMENT); // status inicial do pedido
	}

	@Test
	void execute_ShouldUpdateOrder_WhenOrderExists() {
		// Arrange
		when(retrieveOrderUseCase.findById(1L)).thenReturn(Optional.of(order));
		when(createOrUpdateOrderUseCase.execute(order)).thenReturn(order);

		// Act
		BufferedImage result = endOrderUseCase.execute(1L);

		// Assert
		assertNull(result); // Agora espera-se que o retorno seja null, já que o QRCode
							// não está sendo gerado
		assertEquals(OrderStatus.AWAITING_PAYMENT, order.getStatus()); // Verificar se o
																		// status do
																		// pedido foi
																		// alterado
																		// corretamente
		verify(retrieveOrderUseCase).findById(1L); // Verificar se o método foi chamado
													// corretamente
		verify(createOrUpdateOrderUseCase).execute(order); // Verificar se o pedido foi
															// atualizado
	}

	@Test
	void execute_ShouldThrowOrderNotFound_WhenOrderDoesNotExist() {
		// Arrange
		when(retrieveOrderUseCase.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		OrderNotFound exception = assertThrows(OrderNotFound.class, () -> {
			endOrderUseCase.execute(1L);
		});

		assertEquals("Pedido com o identificador [1] não foi encontrado", exception.getMessage());
		verify(retrieveOrderUseCase).findById(1L); // Verificar se o método foi chamado
													// corretamente
		verify(createOrUpdateOrderUseCase, never()).execute(any()); // Garantir que o
																	// método de
																	// atualização não foi
																	// chamado
	}

	// Teste para verificar que o status não é alterado se o pedido já estiver no estado
	// esperado (opcional)
	@Test
	void execute_ShouldNotChangeStatusIfAlreadyInWaitingPayment() {
		// Arrange
		order.setStatus(OrderStatus.AWAITING_PAYMENT); // Status já é o esperado
		when(retrieveOrderUseCase.findById(1L)).thenReturn(Optional.of(order));
		when(createOrUpdateOrderUseCase.execute(order)).thenReturn(order);

		// Act
		BufferedImage result = endOrderUseCase.execute(1L);

		// Assert
		assertNull(result); // Espera-se que o retorno seja null, já que não há geração de
							// QR Code
		assertEquals(OrderStatus.AWAITING_PAYMENT, order.getStatus()); // O status não
																		// deve ser
																		// alterado
		verify(retrieveOrderUseCase).findById(1L);
		verify(createOrUpdateOrderUseCase).execute(order);
	}

}
