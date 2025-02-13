package tech.fiap.project.domain.usecase.impl.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UpdateOrderUseCaseImplTest {

    @Mock
    private OrderDataProvider orderDataProvider;

    @InjectMocks
    private UpdateOrderUseCaseImpl updateOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetOrderPaid() {
        // Arrange
        Order order = new Order();
        Order expectedOrder = new Order();
        expectedOrder.setStatus(OrderStatus.PAID);

        when(orderDataProvider.updateStatus(order, OrderStatus.PAID)).thenReturn(expectedOrder);

        // Act
        Order result = updateOrderUseCase.setOrderPaid(order);

        // Assert
        assertEquals(OrderStatus.PAID, result.getStatus());
        verify(orderDataProvider, times(1)).updateStatus(order, OrderStatus.PAID);
    }
}