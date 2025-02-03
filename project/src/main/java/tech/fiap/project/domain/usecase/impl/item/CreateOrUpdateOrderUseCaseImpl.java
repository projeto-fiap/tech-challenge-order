package tech.fiap.project.domain.usecase.impl.item;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.item.InitializeItemUseCase;
import tech.fiap.project.domain.usecase.order.CalculateTotalOrderUseCase;
import tech.fiap.project.domain.usecase.order.CreateOrUpdateOrderUseCase;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CreateOrUpdateOrderUseCaseImpl implements CreateOrUpdateOrderUseCase {

	private final OrderDataProvider orderDataProvider;

	private final RestTemplate restTemplate;

	private final String initializePersonServiceUrl = "http://person-service/api/initialize-person";

	private final InitializeItemUseCase initializeItemUseCaseImpl;

	private final CalculateTotalOrderUseCase calculateTotalOrderUseCase;

	public CreateOrUpdateOrderUseCaseImpl(OrderDataProvider orderDataProvider, RestTemplate restTemplate,
			InitializeItemUseCase initializeItemUseCaseImpl, CalculateTotalOrderUseCase calculateTotalOrderUseCase) {
		this.orderDataProvider = orderDataProvider;
		this.restTemplate = restTemplate;
		this.initializeItemUseCaseImpl = initializeItemUseCaseImpl;
		this.calculateTotalOrderUseCase = calculateTotalOrderUseCase;
	}

	@Override
	public Order execute(Order order) {

		initializeItemUseCaseImpl.execute(order);
		initializePerson(order);

		if (orderDataProvider.retrieveAll(order).isEmpty()) {
			this.initializeOrder(order);
		}
		else {
			this.updateOrder(order);
		}
		return orderDataProvider.create(order);
	}

	private void initializeOrder(Order order) {
		order.setStatus(OrderStatus.PENDING);
		order.setAwaitingTime(Duration.ZERO);
		order.setCreatedDate(LocalDateTime.now());
		order.setTotalPrice(calculateTotalOrderUseCase.execute(order.getItems()));
	}

	private void updateOrder(Order order) {
		order.setUpdatedDate(LocalDateTime.now());
		order.setTotalPrice(calculateTotalOrderUseCase.execute(order.getItems()));
	}

	// Método para inicializar a pessoa via chamada HTTP
	private void initializePerson(Order order) {
		// Criando cabeçalhos da requisição
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		// Criando a entidade para a requisição
		HttpEntity<Order> entity = new HttpEntity<>(order, headers);

		// Realizando a requisição HTTP
		ResponseEntity<Void> response = restTemplate.exchange(initializePersonServiceUrl, // URL
				// do
				// serviço
				// de
				// inicialização
				// de
				// pessoa
				HttpMethod.POST, // Método POST para criar a pessoa
				entity, Void.class // Tipo esperado da resposta (sem corpo)
		);

		if (!response.getStatusCode().is2xxSuccessful()) {
			// Lidar com erro se necessário, como lançar uma exceção personalizada
			throw new RuntimeException("Failed to initialize person for order " + order.getId());
		}
	}

}
