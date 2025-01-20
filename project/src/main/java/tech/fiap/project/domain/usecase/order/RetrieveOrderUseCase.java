package tech.fiap.project.domain.usecase.order;

import tech.fiap.project.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface RetrieveOrderUseCase {

	List<Order> findAll();

	List<Order> findAllById(List<Long> ids);

	Optional<Order> findById(Long id);

	Optional<Order> findByIdWithPayment(Long id);

}
