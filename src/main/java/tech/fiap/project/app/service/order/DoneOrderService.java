package tech.fiap.project.app.service.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;
import tech.fiap.project.domain.usecase.order.UpdateOrderUseCase;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class DoneOrderService {

	private RetrieveOrderUseCase retrieveOrderUseCase;

	private UpdateOrderUseCase updateOrderUseCase;

	public Optional<OrderResponseDTO> execute(Long id) {
		Optional<Order> order = retrieveOrderUseCase.findByIdWithPayment(id);
		if (order.isPresent()) {
			Order orderUpdated = updateOrderUseCase.setOrderDone(order.get());
			return Optional.of(OrderMapper.toDTO(orderUpdated));
		}
		else {
			return Optional.empty();
		}
	}

}
