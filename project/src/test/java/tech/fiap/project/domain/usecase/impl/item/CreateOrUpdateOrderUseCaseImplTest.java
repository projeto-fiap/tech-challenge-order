package tech.fiap.project.domain.usecase.impl.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.app.dto.DocumentDTO;
import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.domain.usecase.item.InitializeItemUseCase;
import tech.fiap.project.domain.usecase.order.CalculateTotalOrderUseCase;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateOrUpdateOrderUseCaseImplTest {

    @Mock
    private OrderDataProvider orderDataProvider;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private InitializeItemUseCase initializeItemUseCase;

    @Mock
    private CalculateTotalOrderUseCase calculateTotalOrderUseCase;

    @InjectMocks
    private CreateOrUpdateOrderUseCaseImpl createOrUpdateOrderUseCase;

    private final String personUrl = "http://person-service/person";


    @Test
    void testExecute_RetrievePersonFailure() {
        // Arrange
        Order order = createOrder();

        // Simula uma falha na chamada HTTP para recuperar a pessoa
        lenient().when(restTemplate.exchange(
                eq(personUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<List<PersonDTO>>() {})
        )).thenThrow(new IllegalArgumentException("Username must not be null"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createOrUpdateOrderUseCase.execute(order);
        });

        // Verifica a mensagem da exceção
        assertEquals("Username must not be null", exception.getMessage());

        // Verifica se o orderDataProvider.create nunca foi chamado
        verify(orderDataProvider, never()).create(order);
    }



    private Order createOrder() {
        Order order = new Order();
        PersonDTO person = new PersonDTO();
        DocumentDTO document = new DocumentDTO();
        document.setValue("123456789");
        person.setDocument(Collections.singletonList(document));
        order.setPerson(person);
        return order;
    }
}