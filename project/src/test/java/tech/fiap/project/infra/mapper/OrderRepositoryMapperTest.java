package tech.fiap.project.infra.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.entity.Item;
import tech.fiap.project.infra.entity.OrderEntity;
import tech.fiap.project.infra.entity.ItemEntity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class OrderRepositoryMapperTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderRepositoryMapper orderRepositoryMapper;

    private Order order;
    private OrderEntity orderEntity;
    private Item item;
    private ItemEntity itemEntity;
    private PersonDTO personDto;

    @BeforeEach
    void setUp() {
        // O Mockito irá injetar o OrderRepositoryMapper automaticamente

        // Restante da configuração dos objetos de teste
        order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedDate(LocalDateTime.now());
        order.setUpdatedDate(LocalDateTime.now());
        order.setAwaitingTime(Duration.ofDays(30));
        order.setTotalPrice(BigDecimal.valueOf(100.0));

        item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(BigDecimal.valueOf(50.0));
        item.setIngredients(List.of()); // Inicializa a lista de ingredientes
        order.setItems(List.of(item));

        personDto = new PersonDTO();
        personDto.setId(1L);
        personDto.setName("John Doe");
        order.setPerson(personDto);

        orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatus("PENDING");
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setUpdatedDate(LocalDateTime.now());
        orderEntity.setAwaitingTime(Duration.ofDays(30));
        orderEntity.setTotalPrice(BigDecimal.valueOf(100.0));
        orderEntity.setPersonId(1L);

        itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setName("Item 1");
        itemEntity.setPrice(BigDecimal.valueOf(50.0));
        orderEntity.setItems(List.of(itemEntity));
    }

    @Test
    void testToEntity() {
        // Act
        OrderEntity result = OrderRepositoryMapper.toEntity(order);

        // Assert
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getStatus().name(), result.getStatus());
        assertEquals(order.getCreatedDate(), result.getCreatedDate());
        assertEquals(order.getUpdatedDate(), result.getUpdatedDate());
        assertEquals(order.getAwaitingTime(), result.getAwaitingTime());
        assertEquals(order.getTotalPrice(), result.getTotalPrice());
        assertEquals(order.getPerson().getId(), result.getPersonId());
        assertEquals(1, result.getItems().size());
        assertEquals(item.getId(), result.getItems().get(0).getId());
        assertEquals(item.getName(), result.getItems().get(0).getName());
        assertEquals(item.getPrice(), result.getItems().get(0).getPrice());
    }

    @Test
    void testToDomain_NullInput() {
        // Act
        Order result = orderRepositoryMapper.toDomain((OrderEntity) null);

        // Assert
        assertNull(result);
    }

    @Test
    void testToEntity_NullInput() {
        // Act
        OrderEntity result = OrderRepositoryMapper.toEntity(null);

        // Assert
        assertNull(result);
    }
}
