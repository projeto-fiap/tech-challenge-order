package tech.fiap.project.infra.dataprovider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.infra.entity.OrderEntity;
import tech.fiap.project.infra.mapper.OrderRepositoryMapper;
import tech.fiap.project.infra.repository.OrderRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDataProviderImplTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderRepositoryMapper orderRepositoryMapper;

	@InjectMocks
	private OrderDataProviderImpl orderDataProvider;

	private Order order;

	private OrderEntity orderEntity;

	@BeforeEach
	void setUp() {
		order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.PENDING);

		orderEntity = new OrderEntity();
		orderEntity.setId(1L);
		orderEntity.setStatus(OrderStatus.PENDING.name());
	}

	@Test
	void testRetrieveAllWithOrder() {
		// Arrange
		when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
		when(orderRepositoryMapper.toDomain(orderEntity)).thenReturn(order);

		// Act
		Optional<Order> result = orderDataProvider.retrieveAll(order);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(order.getId(), result.get().getId());
		verify(orderRepository, times(1)).findById(1L);
		verify(orderRepositoryMapper, times(1)).toDomain(orderEntity);
	}

	@Test
	void testRetrieveAllWithOrderNotFound() {
		// Arrange
		when(orderRepository.findById(1L)).thenReturn(Optional.empty());

		// Act
		Optional<Order> result = orderDataProvider.retrieveAll(order);

		// Assert
		assertFalse(result.isPresent());
		verify(orderRepository, times(1)).findById(1L);
	}

	@Test
	void testRetrieveAll() {
		// Arrange
		when(orderRepository.findAll()).thenReturn(Collections.singletonList(orderEntity));
		when(orderRepositoryMapper.toDomain(Collections.singletonList(orderEntity)))
				.thenReturn(Collections.singletonList(order));

		// Act
		List<Order> result = orderDataProvider.retrieveAll();

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(order.getId(), result.get(0).getId());
		verify(orderRepository, times(1)).findAll();
		verify(orderRepositoryMapper, times(1)).toDomain(Collections.singletonList(orderEntity));
	}

	@Test
	void testRetrieveAllById() {
		// Arrange
		List<Long> ids = Collections.singletonList(1L);
		when(orderRepository.findAllById(ids)).thenReturn(Collections.singletonList(orderEntity));
		when(orderRepositoryMapper.toDomain(Collections.singletonList(orderEntity)))
				.thenReturn(Collections.singletonList(order));

		// Act
		List<Order> result = orderDataProvider.retrieveAllById(ids);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(order.getId(), result.get(0).getId());
		verify(orderRepository, times(1)).findAllById(ids);
		verify(orderRepositoryMapper, times(1)).toDomain(Collections.singletonList(orderEntity));
	}

	@Test
	void testRetrieveById() {
		// Arrange
		when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
		when(orderRepositoryMapper.toDomain(orderEntity)).thenReturn(order);

		// Act
		Optional<Order> result = orderDataProvider.retrieveById(1L);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(order.getId(), result.get().getId());
		verify(orderRepository, times(1)).findById(1L);
		verify(orderRepositoryMapper, times(1)).toDomain(orderEntity);
	}

	@Test
	void testRetrieveByIdWithPayment() {
		// Arrange
		when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
		when(orderRepositoryMapper.toDomain(orderEntity)).thenReturn(order);

		// Act
		Optional<Order> result = orderDataProvider.retrieveByIdWithPayment(1L);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(order.getId(), result.get().getId());
		verify(orderRepository, times(1)).findById(1L);
		verify(orderRepositoryMapper, times(1)).toDomain(orderEntity);
	}

}
