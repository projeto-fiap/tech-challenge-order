package tech.fiap.project.domain.usecase.impl.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.CreateQrCodeUseCase;
import tech.fiap.project.domain.usecase.order.CreateOrUpdateOrderUseCase;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;
import tech.fiap.project.infra.exception.OrderNotFound;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndOrderUseCaseImplTest {

	@Mock
	private CreateOrUpdateOrderUseCase createOrUpdateOrderUseCase;

	@Mock
	private RetrieveOrderUseCase retrieveOrderUseCase;

	@Mock
	private CreateQrCodeUseCase generateQrCode;

	@InjectMocks
	private EndOrderUseCaseImpl endOrderUseCaseImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testExecute() throws IOException {
		Long orderId = 1L;
		Order order = new Order();
		order.setStatus(OrderStatus.AWAITING_PAYMENT);
		BufferedImage mockImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

		when(retrieveOrderUseCase.findById(orderId)).thenReturn(Optional.of(order));
		when(createOrUpdateOrderUseCase.execute(order)).thenReturn(order);
		when(generateQrCode.execute(order)).thenReturn(mockImage);

		BufferedImage result = endOrderUseCaseImpl.execute(orderId);

		assertNotNull(result);
		assertEquals(mockImage, result);
		verify(retrieveOrderUseCase, times(1)).findById(orderId);
		verify(createOrUpdateOrderUseCase, times(1)).execute(order);
		verify(generateQrCode, times(1)).execute(order);
	}

}
