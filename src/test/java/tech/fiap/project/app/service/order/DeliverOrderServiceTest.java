package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.KitchenDTO;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.KitchenStatus;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.order.DeliverOrderUseCase;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;
import tech.fiap.project.infra.exception.KitchenStatusException;
import tech.fiap.project.infra.exception.OrderNotFound;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliverOrderServiceTest {

	@Mock
	private DeliverOrderUseCase deliverOrderUseCase;

	@Mock
	private RetrieveOrderUseCase retrieveOrderUseCase;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private DeliverOrderService deliverOrderService;

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
	void testExecute_OrderFoundAndDelivered() {
		Long orderId = 1L;
		Order order = new Order();
		order.setId(orderId);
		order.setStatus(OrderStatus.PENDING);
		Order deliveredOrder = new Order();
		deliveredOrder.setId(orderId);
		deliveredOrder.setStatus(OrderStatus.FINISHED);
		KitchenDTO kitchenDTO = new KitchenDTO();
		kitchenDTO.setOrderId(orderId);
		kitchenDTO.setStatus(KitchenStatus.DONE);
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		orderResponseDTO.setId(orderId);

		when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.of(order));
		when(restTemplate.exchange(eq("http://kitchen-service/api/kitchen/" + orderId), eq(HttpMethod.GET),
				any(HttpEntity.class), eq(KitchenDTO.class)))
						.thenReturn(new ResponseEntity<>(kitchenDTO, HttpStatus.OK));
		when(deliverOrderUseCase.execute(orderId)).thenReturn(deliveredOrder);
		orderMapperMockedStatic.when(() -> OrderMapper.toDTO(order, Optional.of(kitchenDTO)))
				.thenReturn(orderResponseDTO);
		orderMapperMockedStatic.when(() -> OrderMapper.toResponse(deliveredOrder)).thenReturn(orderResponseDTO);

		OrderResponseDTO result = deliverOrderService.execute(orderId);

		assertNotNull(result);
		assertEquals(orderResponseDTO.getId(), result.getId());
		verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(orderId);
		verify(restTemplate, times(1)).exchange(eq("http://kitchen-service/api/kitchen/" + orderId), eq(HttpMethod.GET),
				any(HttpEntity.class), eq(KitchenDTO.class));
		verify(deliverOrderUseCase, times(1)).execute(orderId);
		orderMapperMockedStatic.verify(() -> OrderMapper.toDTO(order, Optional.of(kitchenDTO)), times(1));
		orderMapperMockedStatic.verify(() -> OrderMapper.toResponse(deliveredOrder), times(1));
	}

	// @Test
	// void testExecute_OrderNotFound() {
	// Long orderId = 1L;
	//
	// when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.empty());
	//
	// assertThrows(OrderNotFound.class, () -> deliverOrderService.execute(orderId));
	// verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(orderId);
	// verify(restTemplate, never()).exchange(anyString(), any(HttpMethod.class),
	// any(HttpEntity.class), eq(KitchenDTO.class));
	// verify(deliverOrderUseCase, never()).execute(orderId);
	// orderMapperMockedStatic.verifyNoInteractions();
	// }
	//
	// @Test
	// void testExecute_KitchenStatusNotDone() {
	// Long orderId = 1L;
	// Order order = new Order();
	// order.setId(orderId);
	// order.setStatus(OrderStatus.PENDING);
	// KitchenDTO kitchenDTO = new KitchenDTO();
	// kitchenDTO.setOrderId(orderId);
	// kitchenDTO.setStatus(KitchenStatus.IN_PRODUCTION);
	//
	// when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.of(order));
	// when(restTemplate.exchange(eq("http://kitchen-service/api/kitchen/" + orderId),
	// eq(HttpMethod.GET), any(HttpEntity.class), eq(KitchenDTO.class)))
	// .thenReturn(new ResponseEntity<>(kitchenDTO, HttpStatus.OK));
	// orderMapperMockedStatic.when(() -> OrderMapper.toDTO(order,
	// Optional.of(kitchenDTO))).thenReturn(new OrderResponseDTO());
	//
	// assertThrows(KitchenStatusException.class, () ->
	// deliverOrderService.execute(orderId));
	// verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(orderId);
	// verify(restTemplate, times(1)).exchange(eq("http://kitchen-service/api/kitchen/" +
	// orderId), eq(HttpMethod.GET), any(HttpEntity.class), eq(KitchenDTO.class));
	// verify(deliverOrderUseCase, never()).execute(orderId);
	// orderMapperMockedStatic.verify(() -> OrderMapper.toDTO(order,
	// Optional.of(kitchenDTO)), times(1));
	// orderMapperMockedStatic.verifyNoMoreInteractions();
	// }
	//
	// @Test
	// void testExecute_OrderAlreadyFinished() {
	// Long orderId = 1L;
	// Order order = new Order();
	// order.setId(orderId);
	// order.setStatus(OrderStatus.FINISHED);
	//
	// when(retrieveOrderUseCase.findByIdWithPayment(orderId)).thenReturn(Optional.of(order));
	//
	// assertThrows(OrderNotFound.class, () -> deliverOrderService.execute(orderId));
	// verify(retrieveOrderUseCase, times(1)).findByIdWithPayment(orderId);
	// verify(restTemplate, never()).exchange(anyString(), any(HttpMethod.class),
	// any(HttpEntity.class), eq(KitchenDTO.class));
	// verify(deliverOrderUseCase, never()).execute(orderId);
	// orderMapperMockedStatic.verifyNoInteractions();
	// }

}
