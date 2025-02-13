package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetrieveOrderServiceTest {

	@Mock
	private RetrieveOrderUseCase retrieveOrderUseCase;

	@InjectMocks
	private RetrieveOrderService retrieveOrderService;

	@Test
	void testFindAll() {
		Order order1 = new Order();
		order1.setId(1L);
		order1.setStatus(OrderStatus.AWAITING_PAYMENT);
		order1.setCreatedDate(LocalDateTime.now().minusMinutes(10));
		order1.setUpdatedDate(LocalDateTime.now());
		order1.setItems(Collections.emptyList()); // Evitar NullPointerException
		order1.setPayments(Collections.emptyList()); // Evitar NullPointerException
		order1.setTotalPrice(new BigDecimal("50.00"));

		Order order2 = new Order();
		order2.setId(2L);
		order2.setStatus(OrderStatus.FINISHED);
		order2.setCreatedDate(LocalDateTime.now().minusMinutes(5));
		order2.setUpdatedDate(LocalDateTime.now());
		order2.setItems(Collections.emptyList()); // Evitar NullPointerException
		order2.setPayments(Collections.emptyList()); // Evitar NullPointerException
		order2.setTotalPrice(new BigDecimal("150.00"));

		when(retrieveOrderUseCase.findAll()).thenReturn(Arrays.asList(order1, order2));

		var orders = retrieveOrderService.findAll();

		assertEquals(2, orders.size());
		verify(retrieveOrderUseCase, times(1)).findAll();
	}

	@Test
	void testFindById() {
		Order order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.AWAITING_PAYMENT);
		order.setCreatedDate(LocalDateTime.now().minusMinutes(10));
		order.setUpdatedDate(LocalDateTime.now());
		order.setItems(Collections.emptyList()); // Evitar NullPointerException
		order.setPayments(Collections.emptyList()); // Evitar NullPointerException
		order.setTotalPrice(new BigDecimal("100.00"));

		when(retrieveOrderUseCase.findByIdWithPayment(1L)).thenReturn(Optional.of(order));

		var retrievedOrder = retrieveOrderService.findById(1L);

		assertTrue(retrievedOrder.isPresent());
		assertEquals(1L, retrievedOrder.get().getId());
		verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(1L);
	}

	@Test
	void testFindByIdWhenOrderNotFound() {
		when(retrieveOrderUseCase.findByIdWithPayment(999L)).thenReturn(Optional.empty());

		var retrievedOrder = retrieveOrderService.findById(999L);

		assertFalse(retrievedOrder.isPresent());
		verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(999L);
	}

	@Test
	void testSetDuration() {
		OrderResponseDTO order = new OrderResponseDTO();
		LocalDateTime createdDate = LocalDateTime.now().minusMinutes(15);
		LocalDateTime updatedDate = LocalDateTime.now();

		order.setCreatedDate(createdDate);
		order.setUpdatedDate(updatedDate);
		order.setStatus(OrderStatus.AWAITING_PAYMENT);
		order.setItems(Collections.emptyList()); // Evitar NullPointerException
		order.setPayment(Collections.emptyList()); // Evitar NullPointerException

		retrieveOrderService.setDuration(order); // Agora chamamos o método setDuration

		assertNotNull(order.getAwaitingTime()); // Verifica se o tempo de espera foi
												// calculado
		assertEquals(Duration.ofMinutes(15), order.getAwaitingTime()); // Verifica se o
																		// tempo de espera
																		// é o esperado
	}

}
