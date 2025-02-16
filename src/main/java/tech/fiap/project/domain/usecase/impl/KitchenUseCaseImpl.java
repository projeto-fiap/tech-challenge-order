package tech.fiap.project.domain.usecase.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.dto.KitchenDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.usecase.KitchenUseCase;

import java.util.Objects;

@Slf4j
public class KitchenUseCaseImpl implements KitchenUseCase {

	private final RestTemplate restTemplate;

	private final String kitchenServiceUrl;

	private final String clientId;

	private final String keycloakBaseUrl;

	private final String clientSecret;

	public KitchenUseCaseImpl(RestTemplate restTemplate, String kitchenServiceUrl, String clientId,
			String keycloakBaseUrl, String clientSecret) {
		this.restTemplate = restTemplate;
		this.kitchenServiceUrl = kitchenServiceUrl;
		this.clientId = clientId;
		this.keycloakBaseUrl = keycloakBaseUrl;
		this.clientSecret = clientSecret;
	}

	@Override
	public void sendKitchen(Order order) {
		String accessToken = getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<Order> requestEntity = new HttpEntity<>(null, headers);
		String url = String.format("%s/api/v1/kitchen/%s/create", kitchenServiceUrl, order.getId());
		ResponseEntity<KitchenDTO> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				KitchenDTO.class);
		if (exchange.getStatusCode().is2xxSuccessful()) {
			log.info("Order sent to kitchen");
		}
	}

	protected String getAccessToken() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String body = String.format("client_id=%s&client_secret=%s&grant_type=client_credentials", clientId,
				clientSecret);
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<ObjectNode> response = restTemplate.exchange(
				keycloakBaseUrl + "/realms/master/protocol/openid-connect/token", HttpMethod.POST, requestEntity,
				ObjectNode.class);
		return Objects.requireNonNull(response.getBody()).get("access_token").asText();
	}

}
