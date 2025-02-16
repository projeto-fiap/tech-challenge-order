package tech.fiap.project.infra.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.infra.entity.OrderEntity;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryMapperTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private OrderRepositoryMapper orderRepositoryMapper;

	@BeforeEach
	void setUp() throws Exception {
		// Usando reflexão para acessar o construtor privado
		Constructor<OrderRepositoryMapper> constructor = OrderRepositoryMapper.class.getDeclaredConstructor();
		constructor.setAccessible(true); // Permite acesso ao construtor privado
		orderRepositoryMapper = constructor.newInstance();

		// Configura valores simulados para as propriedades injetadas com @Value
		orderRepositoryMapper.personUrl = "http://localhost:8080";
		orderRepositoryMapper.orderClientId = "client-id";
		orderRepositoryMapper.orderClientSecret = "client-secret";
	}

	@Test
	void testToDomain_WithNullOrderEntity() {
		// Act
		Order result = orderRepositoryMapper.toDomain((OrderEntity) null);

		// Assert
		assertNull(result);
	}

	@Test
	void testToDomain_WithoutPersonId() {
		// Arrange
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(1L);
		orderEntity.setStatus("PENDING");
		orderEntity.setCreatedDate(LocalDateTime.now());
		orderEntity.setUpdatedDate(LocalDateTime.now());
		orderEntity.setAwaitingTime(Duration.ofMinutes(10));
		orderEntity.setTotalPrice(BigDecimal.valueOf(100.0));

		// Act
		Order result = orderRepositoryMapper.toDomain(orderEntity);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(OrderStatus.PENDING, result.getStatus());
		assertNull(result.getPerson());
		assertEquals(BigDecimal.valueOf(100.0), result.getTotalPrice());
	}

	@Test
	void testToEntity_WithValidOrder() {
		// Arrange
		Order order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.PENDING);
		order.setCreatedDate(LocalDateTime.now());
		order.setUpdatedDate(LocalDateTime.now());
		order.setAwaitingTime(Duration.ofMinutes(10));
		order.setTotalPrice(BigDecimal.valueOf(100.0));

		// Act
		OrderEntity result = OrderRepositoryMapper.toEntity(order);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("PENDING", result.getStatus());
		assertEquals(order.getCreatedDate(), result.getCreatedDate());
		assertEquals(order.getUpdatedDate(), result.getUpdatedDate());
		assertEquals(order.getAwaitingTime(), result.getAwaitingTime());
		assertEquals(order.getTotalPrice(), result.getTotalPrice());
	}

	@Test
	void testToEntity_WithNullOrder() {
		// Act
		OrderEntity result = OrderRepositoryMapper.toEntity(null);

		// Assert
		assertNull(result);
	}

}