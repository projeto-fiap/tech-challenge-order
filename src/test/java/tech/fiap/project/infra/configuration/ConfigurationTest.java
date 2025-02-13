package tech.fiap.project.infra.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;
import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import tech.fiap.project.domain.usecase.CreateQrCodeUseCase;
import tech.fiap.project.domain.usecase.impl.CreateQrCodeUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.item.CreateItemUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.item.CreateOrUpdateOrderUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.item.RetrieveItemUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.order.CalculateTotalOrderUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.order.RetrieveOrderUseCaseImpl;
import tech.fiap.project.domain.usecase.item.DeleteItemUseCase;
import tech.fiap.project.domain.usecase.item.InitializeItemUseCase;
import tech.fiap.project.domain.usecase.order.*;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfigurationTest {

	@InjectMocks
	private Configuration configuration;

	@Mock
	private ItemDataProvider itemDataProvider;

	@Mock
	private OrderDataProvider orderDataProvider;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private CreateQrCodeUseCase createQrCodeUseCase;

	@Test
	void testObjectMapperBean() {
		// Act
		ObjectMapper objectMapper = configuration.objectMapper();

		// Assert
		assertNotNull(objectMapper);
	}

	@Test
	void testBufferedImageHttpMessageConverterBean() {
		// Act
		HttpMessageConverter<BufferedImage> converter = configuration.createImageHttpMessageConverter();

		// Assert
		assertNotNull(converter);
	}

	@Test
	void testRetrieveItemUseCaseBean() {
		// Act
		RetrieveItemUseCaseImpl retrieveItemUseCase = configuration.retrieveItemUseCase(itemDataProvider);

		// Assert
		assertNotNull(retrieveItemUseCase);
	}

	@Test
	void testCreateItemUseCaseBean() {
		// Act
		CreateItemUseCaseImpl createItemUseCase = configuration.createItemUseCase(itemDataProvider);

		// Assert
		assertNotNull(createItemUseCase);
	}

	@Test
	void testRetrieveOrderUseCaseBean() {
		// Act
		RetrieveOrderUseCaseImpl retrieveOrderUseCase = configuration.retrieveOrderUseCase(orderDataProvider);

		// Assert
		assertNotNull(retrieveOrderUseCase);
	}

	@Test
	void testInitializeItemUseCaseBean() {
		// Act
		InitializeItemUseCase initializeItemUseCase = configuration.initializeItemUseCase(itemDataProvider);

		// Assert
		assertNotNull(initializeItemUseCase);
	}

	@Test
	void testCalculateTotalOrderUseCaseBean() {
		// Act
		CalculateTotalOrderUseCaseImpl calculateTotalOrderUseCase = configuration.calculateTotalOrderUseCase();

		// Assert
		assertNotNull(calculateTotalOrderUseCase);
	}

	@Test
	void testDeliverOrderUseCaseBean() {
		// Arrange
		CreateOrUpdateOrderUseCase createOrUpdateOrderUseCase = mock(CreateOrUpdateOrderUseCase.class);
		RetrieveOrderUseCaseImpl retrieveOrderUseCase = mock(RetrieveOrderUseCaseImpl.class);

		// Act
		DeliverOrderUseCase deliverOrderUseCase = configuration.deliverOrderUseCase(createOrUpdateOrderUseCase,
				retrieveOrderUseCase);

		// Assert
		assertNotNull(deliverOrderUseCase);
	}

	@Test
	void testDeleteItemUseCaseBean() {
		// Act
		DeleteItemUseCase deleteItemUseCase = configuration.deleteItemUseCase(itemDataProvider);

		// Assert
		assertNotNull(deleteItemUseCase);
	}

	@Test
	void testEndOrderUseCaseBean() {
		// Arrange
		CreateOrUpdateOrderUseCase createOrUpdateOrderUseCase = mock(CreateOrUpdateOrderUseCase.class);
		RetrieveOrderUseCaseImpl retrieveOrderUseCase = mock(RetrieveOrderUseCaseImpl.class);

		// Act
		EndOrderUseCase endOrderUseCase = configuration.endOrderUseCase(createOrUpdateOrderUseCase,
				retrieveOrderUseCase, createQrCodeUseCase);

		// Assert
		assertNotNull(endOrderUseCase);
	}

	@Test
	void testCreateOrUpdateOrderUseCaseBean() {
		// Arrange
		InitializeItemUseCase initializeItemUseCase = mock(InitializeItemUseCase.class);

		// Act
		CreateOrUpdateOrderUseCaseImpl createOrUpdateOrderUseCase = configuration.createOrUpdateOrderUseCaseImpl(
				initializeItemUseCase, orderDataProvider, restTemplate, mock(CalculateTotalOrderUseCaseImpl.class));

		// Assert
		assertNotNull(createOrUpdateOrderUseCase);
	}

	@Test
	void testRestTemplateBean() {
		// Act
		RestTemplate restTemplate = configuration.restTemplate();

		// Assert
		assertNotNull(restTemplate);
	}

	@Test
	void testUpdateOrderUseCaseBean() {
		// Act
		UpdateOrderUseCase updateOrderUseCase = configuration.updateOrderUseCase(orderDataProvider);

		// Assert
		assertNotNull(updateOrderUseCase);
	}

	@Test
	void testCreateQrCodeUseCaseBean() {
		// Act
		CreateQrCodeUseCaseImpl createQrCodeUseCase = configuration.createQrCodeUseCase(restTemplate, restTemplate);

		// Assert
		assertNotNull(createQrCodeUseCase);
	}

}