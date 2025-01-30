package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckoutOrderServiceTest {

    @Mock
    private RetrieveOrderUseCase retrieveOrderUseCase;

    @InjectMocks
    private CheckoutOrderService checkoutOrderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PAID);
        order.setItems(List.of()); // Inicializa a lista de items
        order.setPayments(List.of()); // Inicializa a lista de payments
    }

    @Test
    void testExecuteOrderFoundAndPaid() {
        when(retrieveOrderUseCase.findByIdWithPayment(1L)).thenReturn(Optional.of(order));

        Optional<OrderResponseDTO> result = checkoutOrderService.execute(1L);

        assertTrue(result.isPresent());
        verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(1L);
    }

    @Test
    void testExecuteOrderNotFound() {
        when(retrieveOrderUseCase.findByIdWithPayment(1L)).thenReturn(Optional.empty());

        Optional<OrderResponseDTO> result = checkoutOrderService.execute(1L);

        assertFalse(result.isPresent());
        verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(1L);
    }

    @Test
    void testExecuteOrderNotPaid() {
        order.setStatus(OrderStatus.PENDING); // Simulando um status diferente de "PAID"
        when(retrieveOrderUseCase.findByIdWithPayment(1L)).thenReturn(Optional.of(order));

        Optional<OrderResponseDTO> result = checkoutOrderService.execute(1L);

        assertFalse(result.isPresent());
        verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(1L);
    }
}
