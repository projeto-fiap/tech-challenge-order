package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.OrderRequestDTO;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.usecase.order.CreateOrUpdateOrderUseCase;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderServiceTest {

    @Mock
    private CreateOrUpdateOrderUseCase createOrUpdateOrderUsecase;

    @InjectMocks
    private CreateOrderService createOrderService;

    private OrderRequestDTO orderRequestDTO;
    private Order order;
    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    void setUp() {
        // Initialize orderRequestDTO with test data
        orderRequestDTO = new OrderRequestDTO();

        // Initialize order and make sure items and payments are not null
        order = new Order();
        order.setItems(Collections.emptyList());  // Ensure items is initialized
        order.setPayments(Collections.emptyList());  // Ensure payments is initialized

        // Initialize the response DTO
        orderResponseDTO = OrderMapper.toResponse(order);

        // Stub the usecase method to return the order
        doReturn(order).when(createOrUpdateOrderUsecase).execute(any(Order.class));  // Use 'any(Order.class)' to match any Order
    }

    @Test
    void testExecute() {
        OrderResponseDTO result = createOrderService.execute(orderRequestDTO);

        assertNotNull(result);

        // Comparar os atributos manualmente
        assertEquals(orderResponseDTO.getId(), result.getId());
        assertEquals(orderResponseDTO.getStatus(), result.getStatus());
        assertEquals(orderResponseDTO.getCreatedDate(), result.getCreatedDate());
        assertEquals(orderResponseDTO.getUpdatedDate(), result.getUpdatedDate());
        assertEquals(orderResponseDTO.getTotalPrice(), result.getTotalPrice());
        // Comparar outros atributos conforme necessário
        assertEquals(orderResponseDTO.getItems(), result.getItems());
        assertEquals(orderResponseDTO.getPayment(), result.getPayment());
        assertEquals(orderResponseDTO.getPerson(), result.getPerson());
        assertEquals(orderResponseDTO.getAwaitingTime(), result.getAwaitingTime());
        assertEquals(orderResponseDTO.getKitchenQueue(), result.getKitchenQueue());

        // Verificar se o método foi chamado corretamente
        verify(createOrUpdateOrderUsecase, times(1)).execute(any(Order.class));
    }

}
