package tech.fiap.project.infra.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.querydsl.core.types.dsl.PathBuilder;
import tech.fiap.project.infra.entity.QIngredientItemEntity;
import tech.fiap.project.infra.entity.IngredientItemEntity;
import tech.fiap.project.app.dto.IngredientCategory;

import java.math.BigDecimal;

class QIngredientItemEntityTest {

	@Test
	void testQIngredientItemEntityPaths() {
		// Criação da instância de QIngredientItemEntity
		QIngredientItemEntity qIngredientItemEntity = QIngredientItemEntity.ingredientItemEntity;

		// Verifica se os campos estão corretamente mapeados
		assertNotNull(qIngredientItemEntity.id);
		assertEquals("id", qIngredientItemEntity.id.getMetadata().getName());

		assertNotNull(qIngredientItemEntity.name);
		assertEquals("name", qIngredientItemEntity.name.getMetadata().getName());

		assertNotNull(qIngredientItemEntity.description);
		assertEquals("description", qIngredientItemEntity.description.getMetadata().getName());

		assertNotNull(qIngredientItemEntity.currency);
		assertEquals("currency", qIngredientItemEntity.currency.getMetadata().getName());

		assertNotNull(qIngredientItemEntity.imageUrl);
		assertEquals("imageUrl", qIngredientItemEntity.imageUrl.getMetadata().getName());

		assertNotNull(qIngredientItemEntity.category);
		assertEquals("category", qIngredientItemEntity.category.getMetadata().getName());

		assertNotNull(qIngredientItemEntity.price);
		assertEquals("price", qIngredientItemEntity.price.getMetadata().getName());

		assertNotNull(qIngredientItemEntity.quantity);
		assertEquals("quantity", qIngredientItemEntity.quantity.getMetadata().getName());

		assertNotNull(qIngredientItemEntity.item);
		assertEquals("item", qIngredientItemEntity.item.getMetadata().getName());
	}

	@Test
	void testPathBuilderForIngredientItemEntity() {
		// Verifica o uso do PathBuilder para validar os campos da entidade
		PathBuilder<IngredientItemEntity> builder = new PathBuilder<>(IngredientItemEntity.class,
				"ingredientItemEntity");

		// Testa o caminho para "id"
		assertEquals(builder.get("id", Long.class), QIngredientItemEntity.ingredientItemEntity.id);

		// Testa o caminho para "name"
		assertEquals(builder.get("name", String.class), QIngredientItemEntity.ingredientItemEntity.name);

		// Testa o caminho para "description"
		assertEquals(builder.get("description", String.class), QIngredientItemEntity.ingredientItemEntity.description);

		// Testa o caminho para "currency"
		assertEquals(builder.get("currency", String.class), QIngredientItemEntity.ingredientItemEntity.currency);

		// Testa o caminho para "imageUrl"
		assertEquals(builder.get("imageUrl", String.class), QIngredientItemEntity.ingredientItemEntity.imageUrl);

		// Testa o caminho para "category"
		assertEquals(builder.get("category", IngredientCategory.class),
				QIngredientItemEntity.ingredientItemEntity.category);

		// Testa o caminho para "price"
		assertEquals(builder.get("price", BigDecimal.class), QIngredientItemEntity.ingredientItemEntity.price);

		// Testa o caminho para "quantity"
		assertEquals(builder.get("quantity", BigDecimal.class), QIngredientItemEntity.ingredientItemEntity.quantity);

		// Testa o caminho para "item"
		assertEquals(builder.get("item", Object.class), QIngredientItemEntity.ingredientItemEntity.item); // Assuming
																											// QItemEntity
	}

	@Test
	void testQIngredientItemEntityInitialization() {
		// Testa a inicialização e o nome da instância estática de QIngredientItemEntity
		assertNotNull(QIngredientItemEntity.ingredientItemEntity);
		assertEquals("ingredientItemEntity", QIngredientItemEntity.ingredientItemEntity.getMetadata().getName());
	}

}
