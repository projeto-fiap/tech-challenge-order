package tech.fiap.project.infra.mapper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.OrderEntity;

import java.util.List;

public class OrderRepositoryMapper {

	private static final String PERSON_SERVICE_URL = "http://url-do-servico-de-pessoas/";

	private OrderRepositoryMapper() {
	}

	public static List<Order> toDomain(List<OrderEntity> orders) {
		return orders.stream().map(OrderRepositoryMapper::toDomain).toList();
	}

	public static List<OrderEntity> toEntity(List<Order> orders) {
		return orders.stream().map(OrderRepositoryMapper::toEntity).toList();
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

	public static Order toDomain(OrderEntity orderEntity) {
		if (orderEntity == null) {
			return null;
		}

		PersonDTO personDto = null;
		if (orderEntity.getPersonId() != null) {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<PersonDTO> response = restTemplate.exchange(PERSON_SERVICE_URL + orderEntity.getPersonId(),
					HttpMethod.GET, null, new ParameterizedTypeReference<>() {
					});
			personDto = response.getBody();
		}

		List<Item> domainItems = orderEntity.getItems() != null
				? orderEntity.getItems().stream().map(ItemRepositoryMapper::toDomain).toList() : List.of();

		OrderStatus orderStatus = orderEntity.getStatus() != null
				? OrderStatus.valueOf(orderEntity.getStatus().toUpperCase()) : null;

		return new Order(orderEntity.getId(), orderStatus, orderEntity.getCreatedDate(), orderEntity.getUpdatedDate(),
				domainItems, List.of(), orderEntity.getAwaitingTime(), personDto, orderEntity.getTotalPrice());
	}

}
