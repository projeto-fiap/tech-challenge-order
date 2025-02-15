package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.KitchenDTO;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RetrieveOrderServiceTest {

	@Mock
	private RetrieveOrderUseCase retrieveOrderUseCase;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private RetrieveOrderService retrieveOrderService;

	private MockedStatic<OrderMapper> orderMapperMockedStatic;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		orderMapperMockedStatic = mockStatic(OrderMapper.class);
	}

	@AfterEach
	void tearDown() {
		orderMapperMockedStatic.close();
	}

	@Test
	void testFindAll() {
		List<Order> orders = List.of(new Order(), new Order());
		List<OrderResponseDTO> orderResponseDTOs = List.of(new OrderResponseDTO(), new OrderResponseDTO());

		when(retrieveOrderUseCase.findAll()).thenReturn(orders);
		orderMapperMockedStatic.when(() -> OrderMapper.toDTO(orders)).thenReturn(orderResponseDTOs);

		List<OrderResponseDTO> result = retrieveOrderService.findAll();

		assertNotNull(result);
		assertEquals(orderResponseDTOs.size(), result.size());
		verify(retrieveOrderUseCase, times(1)).findAll();
		orderMapperMockedStatic.verify(() -> OrderMapper.toDTO(orders), times(1));
	}

	@Test
	void testFindById() {
		Long orderId = 1L;
		Order order = new Order();
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

		when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.of(order));
		orderMapperMockedStatic.when(() -> OrderMapper.toDTO(order)).thenReturn(orderResponseDTO);

		Optional<OrderResponseDTO> result = retrieveOrderService.findById(orderId);

		assertTrue(result.isPresent());
		verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(orderId);
		orderMapperMockedStatic.verify(() -> OrderMapper.toDTO(order), times(1));
	}

	// @Test
	// void testFindOngoingAll() {
	// Long orderId = 1L;
	// KitchenDTO kitchenDTO = new KitchenDTO();
	// kitchenDTO.setOrderId(orderId);
	// List<KitchenDTO> kitchenDTOs = List.of(kitchenDTO);
	// List<Long> kitchenIds =
	// kitchenDTOs.stream().map(KitchenDTO::getOrderId).collect(Collectors.toList());
	// Order order = new Order();
	// order.setId(orderId);
	// List<Order> orders = List.of(order);
	// OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
	// orderResponseDTO.setId(orderId);
	// List<OrderResponseDTO> orderResponseDTOs = List.of(orderResponseDTO);
	//
	// when(restTemplate.exchange(eq("http://kitchen-service/api/kitchen"),
	// eq(HttpMethod.GET), any(HttpEntity.class),
	// any(ParameterizedTypeReference.class))).thenReturn(new
	// ResponseEntity<>(kitchenDTOs, HttpStatus.OK));
	// when(retrieveOrderUseCase.findAllById(kitchenIds)).thenReturn(orders);
	// orderMapperMockedStatic.when(() -> OrderMapper.toDTO(orders,
	// kitchenDTOs)).thenReturn(orderResponseDTOs);
	//
	// List<OrderResponseDTO> result = retrieveOrderService.findOngoingAll();
	//
	// assertNotNull(result);
	// assertEquals(orderResponseDTOs.size(), result.size());
	// verify(restTemplate, times(1)).exchange(eq("http://kitchen-service/api/kitchen"),
	// eq(HttpMethod.GET),
	// any(HttpEntity.class), any(ParameterizedTypeReference.class));
	// verify(retrieveOrderUseCase, times(1)).findAllById(kitchenIds);
	// orderMapperMockedStatic.verify(() -> OrderMapper.toDTO(orders, kitchenDTOs),
	// times(1));
	// }

	@Test
	void testSetDuration() {
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		orderResponseDTO.setCreatedDate(LocalDateTime.now().minusHours(2));
		orderResponseDTO.setUpdatedDate(LocalDateTime.now());

		retrieveOrderService.setDuration(orderResponseDTO);

		assertNotNull(orderResponseDTO.getAwaitingTime());
	}

}
