package tech.fiap.project.domain.usecase.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.dto.KitchenDTO;
import tech.fiap.project.domain.entity.Order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KitchenUseCaseImplTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private KitchenUseCaseImpl kitchenUseCase;

	private final String keycloakBaseUrl = "http://localhost:29000";

	private final String kitchenServiceUrl = "http://localhost:8083";

	private final String clientId = "1234567890";

	private final String clientSecret = "developer";

	private Order order;

	@BeforeEach
	void setUp() {
		kitchenUseCase = new KitchenUseCaseImpl(restTemplate, kitchenServiceUrl, clientId, keycloakBaseUrl,
				clientSecret);
		order = new Order();
		order.setId(1L);
	}

	@Test
	void sendKitchen_ShouldSendOrderToKitchen_WhenTokenIsValid() {
		// Arrange
		String accessToken = "test-token";
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<Order> requestEntity = new HttpEntity<>(null, headers);

		when(restTemplate.exchange(eq(keycloakBaseUrl + "/realms/master/protocol/openid-connect/token"),
				eq(HttpMethod.POST), any(HttpEntity.class), eq(ObjectNode.class)))
						.thenReturn(createTokenResponse(accessToken));

		when(restTemplate.exchange(eq(kitchenServiceUrl + "/api/v1/kitchen/1/create"), eq(HttpMethod.POST),
				eq(requestEntity), eq(KitchenDTO.class)))
						.thenReturn(new ResponseEntity<>(new KitchenDTO(), HttpStatus.OK));

		// Act
		kitchenUseCase.sendKitchen(order);

		// Assert
		verify(restTemplate, times(1)).exchange(eq(keycloakBaseUrl + "/realms/master/protocol/openid-connect/token"),
				eq(HttpMethod.POST), any(HttpEntity.class), eq(ObjectNode.class));

		verify(restTemplate, times(1)).exchange(eq(kitchenServiceUrl + "/api/v1/kitchen/1/create"), eq(HttpMethod.POST),
				eq(requestEntity), eq(KitchenDTO.class));
	}

	@Test
	void sendKitchen_ShouldThrowException_WhenTokenRequestFails() {
		// Arrange
		when(restTemplate.exchange(eq(keycloakBaseUrl + "/realms/master/protocol/openid-connect/token"),
				eq(HttpMethod.POST), any(HttpEntity.class), eq(ObjectNode.class)))
						.thenThrow(new RuntimeException("Token request failed"));

		// Act & Assert
		assertThrows(RuntimeException.class, () -> kitchenUseCase.sendKitchen(order));

		verify(restTemplate, times(1)).exchange(eq(keycloakBaseUrl + "/realms/master/protocol/openid-connect/token"),
				eq(HttpMethod.POST), any(HttpEntity.class), eq(ObjectNode.class));

		verify(restTemplate, never()).exchange(eq(kitchenServiceUrl + "/api/v1/kitchen/1/create"), eq(HttpMethod.POST),
				any(HttpEntity.class), eq(KitchenDTO.class));
	}

	@Test
	void sendKitchen_ShouldThrowException_WhenKitchenRequestFails() {
		// Arrange
		String accessToken = "test-token";
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<Order> requestEntity = new HttpEntity<>(null, headers);

		when(restTemplate.exchange(eq(keycloakBaseUrl + "/realms/master/protocol/openid-connect/token"),
				eq(HttpMethod.POST), any(HttpEntity.class), eq(ObjectNode.class)))
						.thenReturn(createTokenResponse(accessToken));

		when(restTemplate.exchange(eq(kitchenServiceUrl + "/api/v1/kitchen/1/create"), eq(HttpMethod.POST),
				eq(requestEntity), eq(KitchenDTO.class))).thenThrow(new RuntimeException("Kitchen request failed"));

		// Act & Assert
		assertThrows(RuntimeException.class, () -> kitchenUseCase.sendKitchen(order));

		verify(restTemplate, times(1)).exchange(eq(keycloakBaseUrl + "/realms/master/protocol/openid-connect/token"),
				eq(HttpMethod.POST), any(HttpEntity.class), eq(ObjectNode.class));

		verify(restTemplate, times(1)).exchange(eq(kitchenServiceUrl + "/api/v1/kitchen/1/create"), eq(HttpMethod.POST),
				eq(requestEntity), eq(KitchenDTO.class));
	}

	private ResponseEntity<ObjectNode> createTokenResponse(String accessToken) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode tokenResponse = objectMapper.createObjectNode();
		tokenResponse.put("access_token", accessToken);
		return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	}

}