package tech.fiap.project.domain.usecase.impl.order;

import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.order.UpdateOrderUseCase;

public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {

    private final OrderDataProvider orderDataProvider;

    public UpdateOrderUseCaseImpl(OrderDataProvider orderDataProvider) {
        this.orderDataProvider = orderDataProvider;
    }

    @Override
    public Order setOrderPaid(Order order) {
        return orderDataProvider.updateStatus(order, OrderStatus.PAID);
    }

}
