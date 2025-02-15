package tech.fiap.project.app.service.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.fiap.project.app.adapter.ItemMapper;
import tech.fiap.project.app.dto.CreateItemRequestDTO;
import tech.fiap.project.app.dto.ItemDTO;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.domain.usecase.item.CreateItemUseCase;

import java.util.List;

@Service
@AllArgsConstructor
public class CreateItemService {

	private CreateItemUseCase createItemUseCase;

	public List<ItemDTO> createItem(List<CreateItemRequestDTO> itemDTOs) {
		List<Item> itemEntities = itemDTOs.stream().map(ItemMapper::toDomain).toList();
		return createItemUseCase.execute(itemEntities).stream().map(ItemMapper::toDTO).toList();
	}

}
