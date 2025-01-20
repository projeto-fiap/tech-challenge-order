package tech.fiap.project.infra.dataprovider;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.ItemCategory;
import tech.fiap.project.infra.entity.ItemEntity;
import tech.fiap.project.infra.mapper.ItemRepositoryMapper;
import tech.fiap.project.infra.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemDataProviderImpl implements ItemDataProvider {

	private final ItemRepository itemRepository;

	@Override
	public List<Item> retrieveAll() {
		return itemRepository.findAll().stream().map(ItemRepositoryMapper::toDomain).toList();
	}

	@Override
	public Optional<Item> retrieveById(Long id) {
		return itemRepository.findById(id).map(ItemRepositoryMapper::toDomain);
	}

	@Override
	public List<Item> saveAll(List<Item> items) {
		List<ItemEntity> itemEntities = items.stream().map(ItemRepositoryMapper::toEntity).toList();
		return itemRepository.saveAll(itemEntities).stream().map(ItemRepositoryMapper::toDomain).toList();
	}

	@Override
	public Item save(Item item) {
		ItemEntity itemEntity = ItemRepositoryMapper.toEntity(item);
		ItemEntity savedEntity = itemRepository.save(itemEntity);
		return ItemRepositoryMapper.toDomain(savedEntity);
	}

	@Override
	public void deleteById(Long id) {
		itemRepository.deleteById(id);
	}

	@Override
	public List<Item> retrieveByCategory(ItemCategory category) {
		return itemRepository.findByCategory(category).stream().map(ItemRepositoryMapper::toDomain).toList();
	}

}
