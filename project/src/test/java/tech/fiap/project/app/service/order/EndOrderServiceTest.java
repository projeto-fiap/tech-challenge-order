package tech.fiap.project.app.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.fiap.project.domain.usecase.order.EndOrderUseCase;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class EndOrderServiceTest {

	@Mock
	private EndOrderUseCase endOrderUseCase;

	@InjectMocks
	private EndOrderService endOrderService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testExecute() throws Exception {
		Long id = 1L;
		BufferedImage mockImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		when(endOrderUseCase.execute(id)).thenReturn(mockImage);

		BufferedImage result = endOrderService.execute(id);

		assertNotNull(result);
	}
}
