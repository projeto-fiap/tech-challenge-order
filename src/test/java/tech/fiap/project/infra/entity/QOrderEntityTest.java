package tech.fiap.project.infra.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.querydsl.core.types.dsl.PathBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

class QOrderEntityTest {

	@Test
	void testQOrderEntityPaths() {
		// Criação da instância de QOrderEntity
		QOrderEntity qOrderEntity = QOrderEntity.orderEntity;

		// Verifica se os campos estão corretamente mapeados
		assertNotNull(qOrderEntity.id);
		assertEquals("id", qOrderEntity.id.getMetadata().getName());

		assertNotNull(qOrderEntity.status);
		assertEquals("status", qOrderEntity.status.getMetadata().getName());

		assertNotNull(qOrderEntity.totalPrice);
		assertEquals("totalPrice", qOrderEntity.totalPrice.getMetadata().getName());

		assertNotNull(qOrderEntity.items);
		assertEquals("items", qOrderEntity.items.getMetadata().getName());

		assertNotNull(qOrderEntity.paymentIds);
		assertEquals("paymentIds", qOrderEntity.paymentIds.getMetadata().getName());

		assertNotNull(qOrderEntity.personId);
		assertEquals("personId", qOrderEntity.personId.getMetadata().getName());

		assertNotNull(qOrderEntity.createdDate);
		assertEquals("createdDate", qOrderEntity.createdDate.getMetadata().getName());

		assertNotNull(qOrderEntity.updatedDate);
		assertEquals("updatedDate", qOrderEntity.updatedDate.getMetadata().getName());

		assertNotNull(qOrderEntity.awaitingTime);
		assertEquals("awaitingTime", qOrderEntity.awaitingTime.getMetadata().getName());
	}

	@Test
	void testPathBuilderForOrderEntity() {
		// Verifica o uso do PathBuilder para validar os campos da entidade
		PathBuilder<OrderEntity> builder = new PathBuilder<>(OrderEntity.class, "orderEntity");

		// Testa o caminho para o "id"
		assertEquals(builder.get("id", Long.class), QOrderEntity.orderEntity.id);

		// Testa o caminho para o "status"
		assertEquals(builder.get("status", String.class), QOrderEntity.orderEntity.status);

		// Testa o caminho para o "totalPrice"
		assertEquals(builder.get("totalPrice", BigDecimal.class), QOrderEntity.orderEntity.totalPrice);

		// Testa o caminho para "items" (lista de ItemEntity)
		assertEquals(builder.get("items", List.class), QOrderEntity.orderEntity.items);

		// Testa o caminho para "paymentIds" (lista de Long)
		assertEquals(builder.get("paymentIds", List.class), QOrderEntity.orderEntity.paymentIds);

		// Testa o caminho para "personId"
		assertEquals(builder.get("personId", Long.class), QOrderEntity.orderEntity.personId);

		// Testa o caminho para "createdDate"
		assertEquals(builder.get("createdDate", LocalDateTime.class), QOrderEntity.orderEntity.createdDate);

		// Testa o caminho para "updatedDate"
		assertEquals(builder.get("updatedDate", LocalDateTime.class), QOrderEntity.orderEntity.updatedDate);

		// Testa o caminho para "awaitingTime"
		assertEquals(builder.get("awaitingTime", Duration.class), QOrderEntity.orderEntity.awaitingTime);
	}

	@Test
	void testQOrderEntityInitialization() {
		// Testa a inicialização e o nome da instância estático de QOrderEntity
		assertNotNull(QOrderEntity.orderEntity);
		assertEquals("orderEntity", QOrderEntity.orderEntity.getMetadata().getName());
	}

}