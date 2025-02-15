package tech.fiap.project.infra.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.OrderEntity;

import java.util.List;

@Component
public class OrderRepositoryMapper {

	@Value("${tech-challenge.person.url}")
	String personUrl;

	private String pathUrl = "/api/v1/person/";

	@Value("${tech-challenge.order.client-id}")
	String orderClientId;

	@Value("${tech-challenge.order.client-secret}")
	String orderClientSecret;

	private OrderRepositoryMapper() {
	}

	public List<Order> toDomain(List<OrderEntity> orders) {
		return orders.stream().map(this::toDomain).toList();
	}

	public static OrderEntity toEntity(Order order) {
		if (order == null) {
			return null;
		}
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(order.getId());

		if (order.getItems() != null) {
			orderEntity.setItems(order.getItems().stream().map(ItemRepositoryMapper::toEntity).toList());
		}

		orderEntity.setStatus(order.getStatus().name());
		orderEntity.setCreatedDate(order.getCreatedDate());
		orderEntity.setUpdatedDate(order.getUpdatedDate());

		if (order.getPerson() != null) {
			orderEntity.setPersonId(order.getPerson().getId());
		}

		orderEntity.setAwaitingTime(order.getAwaitingTime());
		orderEntity.setTotalPrice(order.getTotalPrice());

		return orderEntity;
	}

	public Order toDomain(OrderEntity orderEntity) {
		if (orderEntity == null) {
			return null;
		}

		PersonDTO personDto = null;
		if (orderEntity.getPersonId() != null) {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			headers.setBasicAuth(orderClientId, orderClientSecret);
			HttpEntity<Void> entity = new HttpEntity<>(headers);
			ResponseEntity<PersonDTO> response = restTemplate.exchange(personUrl + pathUrl + orderEntity.getPersonId(),
					HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
					});
			personDto = response.getBody();
			personDto.setPassword(null);
		}

		List<Item> domainItems = orderEntity.getItems() != null
				? orderEntity.getItems().stream().map(ItemRepositoryMapper::toDomain).toList() : List.of();

		OrderStatus orderStatus = orderEntity.getStatus() != null
				? OrderStatus.valueOf(orderEntity.getStatus().toUpperCase()) : null;

		return new Order(orderEntity.getId(), orderStatus, orderEntity.getCreatedDate(), orderEntity.getUpdatedDate(),
				domainItems, List.of(), orderEntity.getAwaitingTime(), personDto, orderEntity.getTotalPrice());
	}

}
