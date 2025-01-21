package tech.fiap.project.infra.mapper;

import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.app.dto.PaymentDTO;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.infra.entity.ItemEntity;
import tech.fiap.project.infra.entity.OrderEntity;

import java.util.List;

public class OrderRepositoryMapper {

	private OrderRepositoryMapper() {
	}

	// public static List<Order> toDomain(List<OrderEntity> orders) {
	// return orders.stream().map(OrderRepositoryMapper::toDomain).toList();
	// }
	//
	// public static List<OrderEntity> toEntity(List<Order> orders) {
	// return orders.stream().map(OrderRepositoryMapper::toEntity).toList();
	// }

	// public static OrderEntity toEntity(Order order) {
	// if (order == null) {
	// return null;
	// }
	// OrderEntity orderEntity = new OrderEntity();
	// orderEntity.setId(order.getId());
	//
	// if (order.getItems() != null) {
	// orderEntity.setItems(order.getItems().stream().map(ItemRepositoryMapper::toEntity).toList());
	// }
	// if (order.getPayments() != null) {
	// orderEntity.setPayments(order.getPayments().stream().map(PaymentRepositoryMapper::toDTO).toList());
	// }
	// orderEntity.setStatus(order.getStatus().name());
	// orderEntity.setCreatedDate(order.getCreatedDate());
	// orderEntity.setUpdatedDate(order.getUpdatedDate());
	// orderEntity.setPerson(PersonRepositoryMapper.toDTO(order.getPerson()));
	// orderEntity.setAwaitingTime(order.getAwaitingTime());
	// orderEntity.setTotalPrice(order.getTotalPrice());
	// return orderEntity;
	// }

	// public static OrderEntity toEntityWithoutPayment(Order order) {
	// if (order == null) {
	// return null;
	// }
	// OrderEntity orderEntity = new OrderEntity();
	// orderEntity.setId(order.getId());
	//
	// if (order.getItems() != null) {
	// orderEntity.setItems(order.getItems().stream().map(ItemRepositoryMapper::toEntity).toList());
	// }
	// orderEntity.setStatus(order.getStatus().name());
	// orderEntity.setCreatedDate(order.getCreatedDate());
	// orderEntity.setUpdatedDate(order.getUpdatedDate());
	// orderEntity.setPerson(PersonRepositoryMapper.toDTO(order.getPerson()));
	// orderEntity.setAwaitingTime(order.getAwaitingTime());
	// orderEntity.setTotalPrice(order.getTotalPrice());
	// return orderEntity;
	// }
	//
	// public static Order toDomain(OrderEntity orderEntity) {
	// if (orderEntity == null) {
	// return null;
	// }
	// PersonDTO domain = null;
	// PersonDTO person = orderEntity.getPerson();
	// if (person != null) {
	// domain = PersonRepositoryMapper.toDomain(person);
	// }
	// List<PaymentDTO> payments = null;
	// if (orderEntity.getPayments() != null) {
	// payments =
	// orderEntity.getPayments().stream().map(PaymentRepositoryMapper::toDomainWithOrder).toList();
	// }
	// List<ItemEntity> items = orderEntity.getItems();
	// if (items == null) {
	// items = List.of();
	// }
	// String status = orderEntity.getStatus();
	// OrderStatus orderStatus;
	// if (status == null) {
	// orderStatus = null;
	// }
	// else {
	// orderStatus = OrderStatus.valueOf(status.toUpperCase());
	// }
	// return new Order(orderEntity.getId(), orderStatus, orderEntity.getCreatedDate(),
	// orderEntity.getUpdatedDate(),
	// items.stream().map(ItemRepositoryMapper::toDomain).toList(), payments,
	// orderEntity.getAwaitingTime(),
	// domain, orderEntity.getTotalPrice());
	// }

	// public static Order toDomainWithoutPayment(OrderEntity orderEntity) {
	// if (orderEntity == null) {
	// return null;
	// }
	// PersonDTO domain = null;
	// PersonDTO person = orderEntity.getPerson();
	// if (person != null) {
	// domain = PersonRepositoryMapper.toDomain(person);
	// }
	// List<PaymentDTO> payments = null;
	// List<ItemEntity> items = orderEntity.getItems();
	// if (items == null) {
	// items = List.of();
	// }
	// String status = orderEntity.getStatus();
	// OrderStatus orderStatus;
	// if (status == null) {
	// orderStatus = null;
	// }
	// else {
	// orderStatus = OrderStatus.valueOf(status.toUpperCase());
	// }
	// return new Order(orderEntity.getId(), orderStatus, orderEntity.getCreatedDate(),
	// orderEntity.getUpdatedDate(),
	// items.stream().map(ItemRepositoryMapper::toDomain).toList(), payments,
	// orderEntity.getAwaitingTime(),
	// domain, orderEntity.getTotalPrice());
	// }

}
