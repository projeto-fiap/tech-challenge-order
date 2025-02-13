package tech.fiap.project.domain.usecase.impl.item;

import org.springframework.stereotype.Service;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.domain.usecase.item.UpdateItemUseCase;

import java.util.List;

@Service
public class UpdateItemUseCaseImpl implements UpdateItemUseCase {

	private final ItemDataProvider itemDataProvider;

	public UpdateItemUseCaseImpl(ItemDataProvider itemDataProvider) {
		this.itemDataProvider = itemDataProvider;
	}

	@Override
	public Item execute(Long id, Item item) {
		return itemDataProvider.retrieveById(id).map(existingItem -> {
			item.setId(id);
			return itemDataProvider.saveAll(List.of(item)).get(0);
		}).orElseThrow(() -> new RuntimeException("Item not found"));
	}

}
