package tech.fiap.project.infra.dataprovider;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.infra.entity.OrderEntity;
import tech.fiap.project.infra.mapper.OrderRepositoryMapper;
import tech.fiap.project.infra.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderDataProviderImpl implements OrderDataProvider {

	private OrderRepository orderRepository;

	private OrderRepositoryMapper orderRepositoryMapper;

	@Override
	public Optional<Order> retrieveAll(Order order) {
		if (order.getId() == null) {
			return Optional.empty();
		}
		else {
			Optional<OrderEntity> orderEntity = orderRepository.findById(order.getId());
			return orderEntity.map(orderRepositoryMapper::toDomain);
		}
	}

	@Override
	public List<Order> retrieveAll() {
		return orderRepositoryMapper.toDomain(orderRepository.findAll());
	}

	@Override
	public List<Order> retrieveAllById(List<Long> id) {
		return orderRepositoryMapper.toDomain(orderRepository.findAllById(id));
	}

	@Override
	public Order create(Order order) {

		OrderEntity entity = OrderRepositoryMapper.toEntity(order);

		OrderEntity orderSaved = orderRepository.save(entity);
		return orderRepositoryMapper.toDomain(orderSaved);
	}

	@Override
	public Order updateStatus(Order order, OrderStatus status) {
		order.setStatus(status);
		OrderEntity entity = OrderRepositoryMapper.toEntity(order);
		OrderEntity save = orderRepository.save(entity);
		return orderRepositoryMapper.toDomain(save);
	}

	@Override
	public Optional<Order> retrieveById(Long id) {
		return orderRepository.findById(id).map(orderRepositoryMapper::toDomain);
	}

	@Override
	public Optional<Order> retrieveByIdWithPayment(Long id) {
		return orderRepository.findById(id).map(orderRepositoryMapper::toDomain);
	}

}
