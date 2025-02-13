package tech.fiap.project.app.service.order;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.KitchenDTO;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.KitchenStatus;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RetrieveOrderService {

	private final RetrieveOrderUseCase retrieveOrderUseCase;

	private final String kitchenServiceUrl = "http://kitchen-service/api/kitchen"; // URL
																					// do
																					// serviço
																					// de
																					// cozinha

	private final RestTemplate restTemplate;

	public List<OrderResponseDTO> findAll() {
		List<OrderResponseDTO> dto = OrderMapper.toDTO(retrieveOrderUseCase.findAll());
		dto.forEach(this::setDuration);
		return dto;
	}

	public Optional<OrderResponseDTO> findById(Long id) {
		Optional<OrderResponseDTO> orderDTO = retrieveOrderUseCase.findByIdWithPayment(id).map(OrderMapper::toDTO);
		orderDTO.ifPresent(this::setDuration);
		return orderDTO;
	}

	public List<OrderResponseDTO> findOngoingAll() {
		// Criando um objeto HttpHeaders
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Criando um objeto HttpEntity com os headers
		HttpEntity<String> entity = new HttpEntity<>(headers);

		// Realizando a requisição GET usando o método exchange
		ResponseEntity<List<KitchenDTO>> response = restTemplate.exchange(kitchenServiceUrl, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<KitchenDTO>>() {
				});

		List<KitchenDTO> kitchenDto = response.getBody();
		if (kitchenDto == null || kitchenDto.isEmpty()) {
			return new ArrayList<>();
		}

		// Extraindo os IDs das ordens da lista de KitchenDTO
		List<Long> kitchenIds = kitchenDto.stream().map(KitchenDTO::getOrderId).collect(Collectors.toList());

		// Obtemos os pedidos associados aos IDs
		List<OrderResponseDTO> dto = OrderMapper.toDTO(retrieveOrderUseCase.findAllById(kitchenIds), kitchenDto);

		return sortByStatusThanDate(dto);
	}

	public void setDuration(OrderResponseDTO order) {
		if (order.getCreatedDate() != null && order.getUpdatedDate() != null) {
			Duration awaitingTime = Duration.between(order.getCreatedDate(), order.getUpdatedDate());
			order.setAwaitingTime(awaitingTime);
		}
	}

	private List<OrderResponseDTO> sortByStatusThanDate(List<OrderResponseDTO> listOrder) {
		List<OrderResponseDTO> filteredListOrder = filterOngoingListOrder(listOrder);
		return sortOngoingListOrder(filteredListOrder);
	}

	private List<OrderResponseDTO> filterOngoingListOrder(List<OrderResponseDTO> listOrder) {
		List<OrderResponseDTO> orderResponseDTOSFiltered = new ArrayList<>();
		listOrder.stream()
				.filter(order -> (order.getKitchenQueue() != null && order.getStatus() != OrderStatus.FINISHED))
				.forEach(orderResponseDTOSFiltered::add);
		return orderResponseDTOSFiltered;
	}

	private List<OrderResponseDTO> sortOngoingListOrder(List<OrderResponseDTO> listOrder) {
		listOrder.sort((order1, order2) -> {
			int statusComparison = priorityCompare(order1, order2);
			if (statusComparison != 0) {
				return statusComparison;
			}
			return order1.getUpdatedDate().compareTo(order2.getUpdatedDate());
		});
		return listOrder;
	}

	private Integer priorityCompare(OrderResponseDTO firstOrder, OrderResponseDTO secondOrder) {
		Map<KitchenStatus, Integer> priorityStatus = getPriorityOrderStatus();
		KitchenStatus firstOrderStatus = firstOrder.getKitchenQueue().getStatus();
		KitchenStatus secondOrderStatus = secondOrder.getKitchenQueue().getStatus();
		return priorityStatus.get(firstOrderStatus).compareTo(priorityStatus.get(secondOrderStatus));
	}

	private Map<KitchenStatus, Integer> getPriorityOrderStatus() {
		Map<KitchenStatus, Integer> statusOrder = new EnumMap<>(KitchenStatus.class);
		statusOrder.put(KitchenStatus.AWAITING_PRODUCTION, 1);
		statusOrder.put(KitchenStatus.IN_PRODUCTION, 2);
		statusOrder.put(KitchenStatus.DONE, 3);
		return statusOrder;
	}

	// Configuração do RestTemplate
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
