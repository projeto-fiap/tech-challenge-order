package tech.fiap.project.app.service.order;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.KitchenDTO;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.KitchenStatus;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.order.DeliverOrderUseCase;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.infra.exception.KitchenStatusException;
import tech.fiap.project.infra.exception.OrderNotFound;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliverOrderService {

	private DeliverOrderUseCase deliverOrderUseCase;

	private RetrieveOrderUseCase retrieveOrderUseCase;

	private final RestTemplate restTemplate;

	private final String kitchenServiceUrl = "http://kitchen-service/api/kitchen"; // URL
																					// do
																					// serviço
																					// de
																					// cozinha

	public OrderResponseDTO execute(Long id) {
		return OrderMapper.toResponse(deliverOrder(id));
	}

	private Order deliverOrder(Long id) {
		// Realizando a requisição HTTP para buscar o estado da cozinha para o pedido
		KitchenDTO kitchenDTO = getKitchenStatus(id);

		// Buscando o pedido pelo ID
		Optional<OrderResponseDTO> orderDTO = retrieveOrderUseCase.findByIdWithPayment(id)
				.map(order -> OrderMapper.toDTO(order, Optional.ofNullable(kitchenDTO)));

		if (orderDTO.isPresent()) {
			var safeOrder = orderDTO.get();
			var safeKitchen = safeOrder.getKitchenQueue();

			// Verificando o status da cozinha
			if (safeKitchen != null && safeKitchen.getStatus() != KitchenStatus.DONE) {
				throw new KitchenStatusException(id);
			}

			// Verificando o status do pedido
			if (safeOrder.getStatus() == OrderStatus.FINISHED) {
				throw new OrderNotFound(id);
			}

			return deliverOrderUseCase.execute(id);
		}

		throw new OrderNotFound(id);
	}

	// Método que faz a requisição HTTP para obter o status da cozinha
	private KitchenDTO getKitchenStatus(Long orderId) {
		// Criando cabeçalhos da requisição
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		// Criando a entidade para a requisição
		HttpEntity<String> entity = new HttpEntity<>(headers);

		// Realizando a requisição HTTP
		ResponseEntity<KitchenDTO> response = restTemplate.exchange(kitchenServiceUrl + "/" + orderId, // URL
																										// com
																										// o
																										// ID
																										// do
																										// pedido
				HttpMethod.GET, entity, KitchenDTO.class // Tipo esperado da resposta
		);

		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			return response.getBody();
		}

		// Caso a resposta não seja bem-sucedida, lançar exceção ou retornar null
		return null;
	}

}
