package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDTOTest {

    @Test
    void testPaymentDTO() {
        Currency currency = Currency.getInstance("BRL");
        OrderResponsePaymentDTO orderResponsePaymentDTO = new OrderResponsePaymentDTO(
                1L,
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                null,
                null,
                null,
                BigDecimal.valueOf(100.50)
        );

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setPaymentDate(LocalDateTime.now());
        paymentDTO.setPaymentMethod("Credit Card");
        paymentDTO.setAmount(BigDecimal.valueOf(100.50));
        paymentDTO.setCurrency(currency);
        paymentDTO.setState(StatePayment.ACCEPTED);
        paymentDTO.setOrder(orderResponsePaymentDTO);

        assertNotNull(paymentDTO);
        assertEquals(1L, paymentDTO.getId());
        assertNotNull(paymentDTO.getPaymentDate());
        assertEquals("Credit Card", paymentDTO.getPaymentMethod());
        assertEquals(BigDecimal.valueOf(100.50), paymentDTO.getAmount());
        assertEquals(currency, paymentDTO.getCurrency());
        assertEquals(StatePayment.ACCEPTED, paymentDTO.getState());
        assertEquals(orderResponsePaymentDTO, paymentDTO.getOrder());
    }

    @Test
    void testEmptyPaymentDTO() {
        PaymentDTO paymentDTO = new PaymentDTO();

        assertNull(paymentDTO.getId());
        assertNull(paymentDTO.getPaymentDate());
        assertNull(paymentDTO.getPaymentMethod());
        assertNull(paymentDTO.getAmount());
        assertNull(paymentDTO.getCurrency());
        assertNull(paymentDTO.getState());
        assertNull(paymentDTO.getOrder());
    }
}
