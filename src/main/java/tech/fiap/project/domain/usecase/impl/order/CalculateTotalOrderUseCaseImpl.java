package tech.fiap.project.domain.usecase.impl.order;

import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.domain.usecase.order.CalculateTotalOrderUseCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CalculateTotalOrderUseCaseImpl implements CalculateTotalOrderUseCase {

	public BigDecimal execute(List<Item> items) {
		AtomicReference<List<Item>> allItems = new AtomicReference<>(new ArrayList<>());
		items.forEach(item -> {

			if (item.getIngredients() != null) {
				allItems.getAndAccumulate(buildListIngredients(item.getIngredients()), (list, ingredients) -> {
					list.addAll(ingredients);
					return list;
				});
			}
			allItems.get().add(item);
		});
		return calculate(allItems.get());
	}

	private static BigDecimal calculate(List<Item> items) {
		return items.stream().map(item -> item.getPrice().multiply(item.getQuantity())).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	}

	private List<Item> buildListIngredients(List<Item> ingredients) {
		List<Item> allIngredients = new ArrayList<>();
		ingredients.forEach(ingredient -> {
			if (ingredient.getIngredients() != null && !ingredient.getIngredients().isEmpty()) {
				allIngredients.addAll(buildListIngredients(ingredient.getIngredients()));
			}
			else {
				allIngredients.add(ingredient);
			}
		});
		return allIngredients;
	}

}
