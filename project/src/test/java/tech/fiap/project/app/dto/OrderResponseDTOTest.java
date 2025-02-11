package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import tech.fiap.project.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseDTOTest {

    @Test
    void testOrderResponseDTO() {
        ItemDTO itemDTO = new ItemDTO();
        PaymentDTO paymentDTO = new PaymentDTO();
        PersonDTO personDTO = new PersonDTO();
        KitchenDTO kitchenDTO = new KitchenDTO();

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(1L);
        orderResponseDTO.setStatus(OrderStatus.PENDING);
        orderResponseDTO.setCreatedDate(LocalDateTime.now());
        orderResponseDTO.setUpdatedDate(LocalDateTime.now().plusHours(1));
        orderResponseDTO.setItems(Collections.singletonList(itemDTO));
        orderResponseDTO.setPayment(Collections.singletonList(paymentDTO));
        orderResponseDTO.setPerson(personDTO);
        orderResponseDTO.setAwaitingTime(Duration.ofMinutes(30));
        orderResponseDTO.setTotalPrice(BigDecimal.valueOf(100.50));
        orderResponseDTO.setKitchenQueue(kitchenDTO);

        assertNotNull(orderResponseDTO);
        assertEquals(1L, orderResponseDTO.getId());
        assertEquals(OrderStatus.PENDING, orderResponseDTO.getStatus());
        assertNotNull(orderResponseDTO.getCreatedDate());
        assertNotNull(orderResponseDTO.getUpdatedDate());
        assertEquals(1, orderResponseDTO.getItems().size());
        assertEquals(itemDTO, orderResponseDTO.getItems().get(0));
        assertEquals(1, orderResponseDTO.getPayment().size());
        assertEquals(paymentDTO, orderResponseDTO.getPayment().get(0));
        assertEquals(personDTO, orderResponseDTO.getPerson());
        assertEquals(Duration.ofMinutes(30), orderResponseDTO.getAwaitingTime());
        assertEquals(BigDecimal.valueOf(100.50), orderResponseDTO.getTotalPrice());
        assertEquals(kitchenDTO, orderResponseDTO.getKitchenQueue());
    }

    @Test
    void testEmptyOrderResponseDTO() {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setItems(new ArrayList<>());  // Inicializando a lista
        orderResponseDTO.setPayment(new ArrayList<>());  // Inicializando a lista

        assertNull(orderResponseDTO.getId());
        assertNull(orderResponseDTO.getStatus());
        assertNull(orderResponseDTO.getCreatedDate());
        assertNull(orderResponseDTO.getUpdatedDate());
        assertTrue(orderResponseDTO.getItems().isEmpty());
        assertTrue(orderResponseDTO.getPayment().isEmpty());
        assertNull(orderResponseDTO.getPerson());
        assertNull(orderResponseDTO.getAwaitingTime());
        assertNull(orderResponseDTO.getTotalPrice());
        assertNull(orderResponseDTO.getKitchenQueue());
    }

}
