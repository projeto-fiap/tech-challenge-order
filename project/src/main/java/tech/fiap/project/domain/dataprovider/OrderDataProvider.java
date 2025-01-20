package tech.fiap.project.domain.dataprovider;

import tech.fiap.project.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDataProvider {

	Optional<Order> retrieveAll(Order order);

	List<Order> retrieveAll();

	List<Order> retrieveAllById(List<Long> id);

	Order create(Order order);

	Optional<Order> retrieveById(Long id);

	Optional<Order> retrieveByIdWithPayment(Long id);

}
