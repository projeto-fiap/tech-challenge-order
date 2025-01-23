package tech.fiap.project.app.service.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.fiap.project.domain.entity.Order;

@Service
@AllArgsConstructor
public class DeliverOrderService {

	// private DeliverOrderUseCase deliverOrderUseCase;

	// private RetrieveOrderUseCase retrieveOrderUseCase;

	// private KitchenRetrieveUseCase kitchenRetrieveUseCase;

	// public OrderResponseDTO execute(Long id) {
	// return OrderMapper.toResponse(deliverOrder(id));
	// }

	private Order deliverOrder(Long id) {
		return null;
	}
	// Optional<Kitchen> kitchen = kitchenRetrieveUseCase.findById(id);
	// Optional<OrderResponseDTO> orderDTO = retrieveOrderUseCase.findByIdWithPayment(id)
	// .map(order -> OrderMapper.toDTO(order, kitchen));
	//
	// if (orderDTO.isPresent()) {
	// var safeOrder = orderDTO.get();
	// var safeKitchen = safeOrder.getKitchenQueue();
	//
	// if (safeKitchen.getStatus() != KitchenStatus.DONE) {
	// throw new KitchenStatusException(id);
	// }
	//
	// if (safeOrder.getStatus() == OrderStatus.FINISHED) {
	// throw new OrderNotFound(id);
	// }
	//
	// return deliverOrderUseCase.execute(id);
	// }
	// throw new OrderNotFound(id);
	// }

}
