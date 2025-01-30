package tech.fiap.project.app.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.fiap.project.domain.usecase.item.DeleteItemUseCase;

@Service
@RequiredArgsConstructor
public class DeleteItemService {

	private final DeleteItemUseCase deleteItemUseCase;

	public void deleteItem(Long id) {
		if (id != null) {
			deleteItemUseCase.execute(id);
		} else {
			throw new IllegalArgumentException("ID cannot be null");
		}
	}

}
