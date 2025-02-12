package tech.fiap.project.domain.dataprovider;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.fiap.project.domain.entity.Order;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderDataProviderTest {

    @Mock
    private OrderDataProvider orderDataProvider;

    @InjectMocks
    private OrderDataProviderTest orderDataProviderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllWithOrder() {
        Order order = new Order();
        when(orderDataProvider.retrieveAll(order)).thenReturn(Optional.of(order));
        Optional<Order> result = orderDataProvider.retrieveAll(order);
        assertThat(result).isPresent();
        verify(orderDataProvider, times(1)).retrieveAll(order);
    }

    @Test
    void testRetrieveAll() {
        when(orderDataProvider.retrieveAll()).thenReturn(Collections.emptyList());
        List<Order> result = orderDataProvider.retrieveAll();
        assertThat(result).isEmpty();
        verify(orderDataProvider, times(1)).retrieveAll();
    }

    @Test
    void testRetrieveAllById() {
        List<Long> ids = Collections.singletonList(1L);
        when(orderDataProvider.retrieveAllById(ids)).thenReturn(Collections.emptyList());
        List<Order> result = orderDataProvider.retrieveAllById(ids);
        assertThat(result).isEmpty();
        verify(orderDataProvider, times(1)).retrieveAllById(ids);
    }

    @Test
    void testCreate() {
        Order order = new Order();
        when(orderDataProvider.create(order)).thenReturn(order);
        Order result = orderDataProvider.create(order);
        assertThat(result).isNotNull();
        verify(orderDataProvider, times(1)).create(order);
    }

    @Test
    void testRetrieveById() {
        Long id = 1L;
        Order order = new Order();
        when(orderDataProvider.retrieveById(id)).thenReturn(Optional.of(order));
        Optional<Order> result = orderDataProvider.retrieveById(id);
        assertThat(result).isPresent();
        verify(orderDataProvider, times(1)).retrieveById(id);
    }

    @Test
    void testRetrieveByIdWithPayment() {
        Long id = 1L;
        Order order = new Order();
        when(orderDataProvider.retrieveByIdWithPayment(id)).thenReturn(Optional.of(order));
        Optional<Order> result = orderDataProvider.retrieveByIdWithPayment(id);
        assertThat(result).isPresent();
        verify(orderDataProvider, times(1)).retrieveByIdWithPayment(id);
    }
}
