package tech.fiap.project.domain.usecase.order;

import tech.fiap.project.domain.entity.Order;

public interface UpdateOrderUseCase {

	Order setOrderPaid(Order order);
	Order setOrderDone(Order order);

}
