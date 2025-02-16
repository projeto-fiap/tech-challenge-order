package tech.fiap.project.app.service.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.fiap.project.app.adapter.OrderMapper;
import tech.fiap.project.app.dto.KitchenDTO;
import tech.fiap.project.app.dto.OrderResponseDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.KitchenUseCase;
import tech.fiap.project.domain.usecase.order.RetrieveOrderUseCase;
import tech.fiap.project.domain.usecase.order.UpdateOrderUseCase;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CheckoutOrderService {

	private RetrieveOrderUseCase retrieveOrderUseCase;

	private UpdateOrderUseCase updateOrderUseCase;

	private KitchenUseCase kitchenUseCase;

	public Optional<OrderResponseDTO> execute(Long id) {
		Optional<Order> order = retrieveOrderUseCase.findByIdWithPayment(id);
		if (order.isPresent()) {
			Order orderUpdated = updateOrderUseCase.setOrderPaid(order.get());
			kitchenUseCase.sendKitchen(orderUpdated);
			return Optional.of(OrderMapper.toDTO(orderUpdated));
		}
		else {
			return Optional.empty();
		}
	}

}
