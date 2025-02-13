package tech.fiap.project.domain.usecase.impl.order;

import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveOrderUseCaseImplTest {

	@Mock
	private OrderDataProvider orderDataProvider;

	@InjectMocks
	private RetrieveOrderUseCaseImpl retrieveOrderUseCase;

	private Order order1;

	private Order order2;

	@BeforeEach
	void setUp() {
		order1 = new Order(); // Configure conforme necessário
		order1.setId(1L);

		order2 = new Order(); // Configure conforme necessário
		order2.setId(2L);
	}

	@Test
	void testFindAll() {

		when(orderDataProvider.retrieveAll()).thenReturn(Arrays.asList(order1, order2));

		List<Order> orders = retrieveOrderUseCase.findAll();

		assertNotNull(orders);
		assertEquals(2, orders.size());
		verify(orderDataProvider).retrieveAll();
	}

	@Test
	void testFindAllById() {

		List<Long> ids = Arrays.asList(1L, 2L);
		when(orderDataProvider.retrieveAll()).thenReturn(Arrays.asList(order1, order2));

		List<Order> orders = retrieveOrderUseCase.findAllById(ids);

		assertNotNull(orders);
		assertEquals(2, orders.size());
		verify(orderDataProvider).retrieveAll();
	}

	@Test
	void testFindById() {

		Long id = 1L;
		when(orderDataProvider.retrieveById(id)).thenReturn(Optional.of(order1));

		Optional<Order> foundOrder = retrieveOrderUseCase.findById(id);

		assertTrue(foundOrder.isPresent());
		assertEquals(order1, foundOrder.get());
		verify(orderDataProvider).retrieveById(id);
	}

	@Test
	void testFindByIdWithPayment() {

		Long id = 1L;
		when(orderDataProvider.retrieveByIdWithPayment(id)).thenReturn(Optional.of(order1));

		Optional<Order> foundOrder = retrieveOrderUseCase.findByIdWithPayment(id);

		assertTrue(foundOrder.isPresent());
		assertEquals(order1, foundOrder.get());
		verify(orderDataProvider).retrieveByIdWithPayment(id);
	}

	@Test
	void testFindByIdNotFound() {

		Long id = 1L;
		when(orderDataProvider.retrieveById(id)).thenReturn(Optional.empty());

		Optional<Order> foundOrder = retrieveOrderUseCase.findById(id);

		assertFalse(foundOrder.isPresent());
		verify(orderDataProvider).retrieveById(id);
	}

}
