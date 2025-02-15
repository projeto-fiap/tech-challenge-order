package tech.fiap.project.domain.usecase.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.domain.entity.Order;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CreateQrCodeUseCaseImplTest {

	@Mock
	private RestTemplate restTemplatePayments;

	@Mock
	private RestTemplate restTemplateKeycloak;

	@InjectMocks
	private CreateQrCodeUseCaseImpl createQrCodeUseCase;

	private final String paymentBaseUrl = "http://localhost:8081";

	private final String keycloakBaseUrl = "http://localhost:8080";

	private final String clientId = "tech-challenge-payments";

	private final String clientSecret = "developer";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		createQrCodeUseCase = new CreateQrCodeUseCaseImpl(restTemplatePayments, restTemplateKeycloak, paymentBaseUrl,
				keycloakBaseUrl, clientId, clientSecret);
	}

	@Test
	void testExecute() throws IOException {
		// Arrange
		Order order = new Order();
		String accessToken = "mock-access-token";

		// Criar uma imagem PNG simples em memória
		BufferedImage mockImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(mockImage, "png", baos);
		byte[] mockQrCodeImage = baos.toByteArray();

		// Mock getAccessToken
		ObjectNode mockTokenBody = mock(ObjectNode.class);
		when(mockTokenBody.get("access_token")).thenReturn(mock(com.fasterxml.jackson.databind.JsonNode.class));
		when(mockTokenBody.get("access_token").asText()).thenReturn(accessToken);
		ResponseEntity<ObjectNode> mockTokenResponse = new ResponseEntity<>(mockTokenBody, HttpStatus.OK);

		when(restTemplateKeycloak.exchange(eq(keycloakBaseUrl + "/realms/master/protocol/openid-connect/token"),
				eq(HttpMethod.POST), any(HttpEntity.class), eq(ObjectNode.class))).thenReturn(mockTokenResponse);

		// Mock payment API response
		when(restTemplatePayments.exchange(eq(paymentBaseUrl + "/api/v1/payments"), eq(HttpMethod.POST),
				any(HttpEntity.class), eq(byte[].class)))
						.thenReturn(new ResponseEntity<>(mockQrCodeImage, HttpStatus.OK));

		// Act
		BufferedImage result = createQrCodeUseCase.execute(order);

		// Assert
		assertNotNull(result, "O resultado não deve ser nulo");
		verify(restTemplateKeycloak, times(1)).exchange(
				eq(keycloakBaseUrl + "/realms/master/protocol/openid-connect/token"), eq(HttpMethod.POST),
				any(HttpEntity.class), eq(ObjectNode.class));
		verify(restTemplatePayments, times(1)).exchange(eq(paymentBaseUrl + "/api/v1/payments"), eq(HttpMethod.POST),
				any(HttpEntity.class), eq(byte[].class));
	}

}