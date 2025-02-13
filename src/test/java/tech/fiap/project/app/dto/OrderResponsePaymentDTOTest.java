package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import tech.fiap.project.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponsePaymentDTOTest {

	@Test
	void testOrderResponsePaymentDTO() {
		ItemDTO itemDTO = new ItemDTO();
		PersonDTO personDTO = new PersonDTO();

		OrderResponsePaymentDTO orderResponsePaymentDTO = new OrderResponsePaymentDTO(1L, OrderStatus.PENDING,
				LocalDateTime.now(), LocalDateTime.now().plusHours(1), Collections.singletonList(itemDTO), personDTO,
				Duration.ofMinutes(30), BigDecimal.valueOf(100.50));

		assertNotNull(orderResponsePaymentDTO);
		assertEquals(1L, orderResponsePaymentDTO.getId());
		assertEquals(OrderStatus.PENDING, orderResponsePaymentDTO.getStatus());
		assertNotNull(orderResponsePaymentDTO.getCreatedDate());
		assertNotNull(orderResponsePaymentDTO.getUpdatedDate());
		assertEquals(1, orderResponsePaymentDTO.getItems().size());
		assertEquals(itemDTO, orderResponsePaymentDTO.getItems().get(0));
		assertEquals(personDTO, orderResponsePaymentDTO.getPerson());
		assertEquals(Duration.ofMinutes(30), orderResponsePaymentDTO.getAwaitingTime());
		assertEquals(BigDecimal.valueOf(100.50), orderResponsePaymentDTO.getTotalPrice());
	}

	@Test
	void testEmptyOrderResponsePaymentDTO() {
		OrderResponsePaymentDTO orderResponsePaymentDTO = new OrderResponsePaymentDTO(null, null, null, null,
				Collections.emptyList(), null, null, null);

		assertNull(orderResponsePaymentDTO.getId());
		assertNull(orderResponsePaymentDTO.getStatus());
		assertNull(orderResponsePaymentDTO.getCreatedDate());
		assertNull(orderResponsePaymentDTO.getUpdatedDate());
		assertTrue(orderResponsePaymentDTO.getItems().isEmpty());
		assertNull(orderResponsePaymentDTO.getPerson());
		assertNull(orderResponsePaymentDTO.getAwaitingTime());
		assertNull(orderResponsePaymentDTO.getTotalPrice());
	}

}
