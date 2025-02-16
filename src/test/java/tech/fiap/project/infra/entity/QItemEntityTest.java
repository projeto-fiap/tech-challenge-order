package tech.fiap.project.infra.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.querydsl.core.types.dsl.PathBuilder;

import java.math.BigDecimal;
import java.util.List;

class QItemEntityTest {

	@Test
	void testQItemEntityPaths() {
		// Criação da instância de QItemEntity
		QItemEntity qItemEntity = QItemEntity.itemEntity;

		// Verifica se os campos estão corretamente mapeados
		assertNotNull(qItemEntity.id);
		assertEquals("id", qItemEntity.id.getMetadata().getName());

		assertNotNull(qItemEntity.name);
		assertEquals("name", qItemEntity.name.getMetadata().getName());

		assertNotNull(qItemEntity.description);
		assertEquals("description", qItemEntity.description.getMetadata().getName());

		assertNotNull(qItemEntity.imageUrl);
		assertEquals("imageUrl", qItemEntity.imageUrl.getMetadata().getName());

		assertNotNull(qItemEntity.ingredients);
		assertEquals("ingredients", qItemEntity.ingredients.getMetadata().getName());

		assertNotNull(qItemEntity.itemCategory);
		assertEquals("itemCategory", qItemEntity.itemCategory.getMetadata().getName());

		assertNotNull(qItemEntity.price);
		assertEquals("price", qItemEntity.price.getMetadata().getName());

		assertNotNull(qItemEntity.quantity);
		assertEquals("quantity", qItemEntity.quantity.getMetadata().getName());

		assertNotNull(qItemEntity.unit);
		assertEquals("unit", qItemEntity.unit.getMetadata().getName());
	}

	@Test
	void testPathBuilderForItemEntity() {
		// Verifica o uso do PathBuilder para validar os campos da entidade
		PathBuilder<ItemEntity> builder = new PathBuilder<>(ItemEntity.class, "itemEntity");

		// Testa o caminho para o "id"
		assertEquals(builder.get("id", Long.class), QItemEntity.itemEntity.id);

		// Testa o caminho para o "name"
		assertEquals(builder.get("name", String.class), QItemEntity.itemEntity.name);

		// Testa o caminho para o "description"
		assertEquals(builder.get("description", String.class), QItemEntity.itemEntity.description);

		// Testa o caminho para "imageUrl"
		assertEquals(builder.get("imageUrl", String.class), QItemEntity.itemEntity.imageUrl);

		// Testa o caminho para "ingredients" (lista de ItemEntity)
		assertEquals(builder.get("ingredients", List.class), QItemEntity.itemEntity.ingredients);

		// Testa o caminho para "itemCategory"
		assertEquals(builder.get("itemCategory", ItemCategory.class), QItemEntity.itemEntity.itemCategory);

		// Testa o caminho para "price"
		assertEquals(builder.get("price", BigDecimal.class), QItemEntity.itemEntity.price);

		// Testa o caminho para "quantity"
		assertEquals(builder.get("quantity", BigDecimal.class), QItemEntity.itemEntity.quantity);

		// Testa o caminho para "unit"
		assertEquals(builder.get("unit", String.class), QItemEntity.itemEntity.unit);
	}

	@Test
	void testQItemEntityInitialization() {
		// Testa a inicialização e o nome da instância estática de QItemEntity
		assertNotNull(QItemEntity.itemEntity);
		assertEquals("itemEntity", QItemEntity.itemEntity.getMetadata().getName());
	}

}
