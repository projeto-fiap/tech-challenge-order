package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.fiap.project.domain.usecase.order.EndOrderUseCase;

import java.awt.image.BufferedImage;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EndOrderServiceTest {

	@Mock
	private EndOrderUseCase endOrderUseCase;

	@InjectMocks
	private EndOrderService endOrderService;

	private Long orderId;

	@BeforeEach
	public void setUp() {
		orderId = 1L;
	}

	@Test
	void testExecuteShouldReturnBufferedImage() {
		BufferedImage expectedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		when(endOrderUseCase.execute(orderId)).thenReturn(expectedImage);

		BufferedImage result = endOrderService.execute(orderId);

		assertNotNull(result);
		assertEquals(expectedImage, result);

		verify(endOrderUseCase, times(1)).execute(orderId);
	}

	@Test
	void testExecuteShouldHandleNullImage() {
		when(endOrderUseCase.execute(orderId)).thenReturn(null);

		BufferedImage result = endOrderService.execute(orderId);

		assertNull(result);

		verify(endOrderUseCase, times(1)).execute(orderId);
	}

}
