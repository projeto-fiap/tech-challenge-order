package tech.fiap.project.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.fiap.project.domain.entity.Order;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CreateQrCodeUseCaseTest {

	@Mock
	private CreateQrCodeUseCase createQrCodeUseCase;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testExecute() throws IOException {
		// Arrange
		Order order = new Order();
		BufferedImage mockImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

		when(createQrCodeUseCase.execute(order)).thenReturn(mockImage);

		// Act
		BufferedImage result = createQrCodeUseCase.execute(order);

		// Assert
		assertNotNull(result);
		verify(createQrCodeUseCase, times(1)).execute(order);
	}

}