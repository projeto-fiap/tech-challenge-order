package tech.fiap.project.domain.usecase.impl.item;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.item.InitializeItemUseCase;
import tech.fiap.project.domain.usecase.order.CalculateTotalOrderUseCase;
import tech.fiap.project.domain.usecase.order.CreateOrUpdateOrderUseCase;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CreateOrUpdateOrderUseCaseImpl implements CreateOrUpdateOrderUseCase {

	private final OrderDataProvider orderDataProvider;

	private final RestTemplate restTemplate;

	private final String personUrl;

	private final String initializePersonServiceUrl = "/api/v1/person";

	private final InitializeItemUseCase initializeItemUseCaseImpl;

	private final CalculateTotalOrderUseCase calculateTotalOrderUseCase;

	private final String username;

	private final String systemPerson;


	public CreateOrUpdateOrderUseCaseImpl(OrderDataProvider orderDataProvider, RestTemplate restTemplate, String personUrl,
                                          InitializeItemUseCase initializeItemUseCaseImpl, CalculateTotalOrderUseCase calculateTotalOrderUseCase, String username, String systemPerson) {
		this.orderDataProvider = orderDataProvider;
		this.restTemplate = restTemplate;
        this.personUrl = personUrl;
        this.initializeItemUseCaseImpl = initializeItemUseCaseImpl;
		this.calculateTotalOrderUseCase = calculateTotalOrderUseCase;
        this.username = username;
        this.systemPerson = systemPerson;
    }

	@Override
	public Order execute(Order order) {

		initializeItemUseCaseImpl.execute(order);
		PersonDTO person = order.getPerson();
		if (person != null) {
			PersonDTO personDTOSaved = retrievePerson(person);
			order.setPerson(personDTOSaved);
		}

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

	private PersonDTO retrievePerson(PersonDTO person) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		headers.setBasicAuth(username, systemPerson);
		HttpEntity<PersonDTO> entity = new HttpEntity<>(person, headers);
		String personServiceUrl = personUrl + initializePersonServiceUrl + "?cpf=" + person.getDocument().stream().findFirst().orElseThrow().getValue();
		ResponseEntity<List<PersonDTO>> response = restTemplate.exchange(personServiceUrl,
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<PersonDTO>>() {}
		);

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Failed to initialize person " + person.getId());
		}
		return Objects.requireNonNull(response.getBody()).stream().findFirst().orElseThrow();
	}

}
