package tech.fiap.project.infra.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import tech.fiap.project.domain.dataprovider.ItemDataProvider;

import tech.fiap.project.domain.dataprovider.OrderDataProvider;
import tech.fiap.project.domain.usecase.impl.item.*;

import tech.fiap.project.domain.usecase.impl.order.CalculateTotalOrderUseCaseImpl;

import tech.fiap.project.domain.usecase.impl.order.DeliverOrderUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.order.EndOrderUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.order.RetrieveOrderUseCaseImpl;
import tech.fiap.project.domain.usecase.item.DeleteItemUseCase;
import tech.fiap.project.domain.usecase.item.InitializeItemUseCase;
import tech.fiap.project.domain.usecase.order.CreateOrUpdateOrderUseCase;
import tech.fiap.project.domain.usecase.order.DeliverOrderUseCase;
import tech.fiap.project.domain.usecase.order.EndOrderUseCase;

import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@org.springframework.context.annotation.Configuration
@Getter
@ComponentScan("tech.fiap.project")
@Setter
public class Configuration {

	@Value("${tech-challenge.orders.base-url}")
	String ordersBaseUrl;

	@Value("${keycloak.base-url}")
	String keycloakBaseUrl;

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
		objectMapper.setDateFormat(df);
		return objectMapper;
	}

	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}

	@Bean
	public RetrieveItemUseCaseImpl retrieveItemUseCase(ItemDataProvider itemDataProvider) {
		return new RetrieveItemUseCaseImpl(itemDataProvider);
	}

	@Bean
	public CreateItemUseCaseImpl createItemUseCase(ItemDataProvider itemDataProvider) {
		return new CreateItemUseCaseImpl(itemDataProvider);
	}

	@Bean
	public RetrieveOrderUseCaseImpl retrieveOrderUseCase(OrderDataProvider orderDataProvider) {
		return new RetrieveOrderUseCaseImpl(orderDataProvider);
	}

	@Bean
	public InitializeItemUseCase initializeItemUseCase(ItemDataProvider itemDataProvider) {
		return new InitializeItemUseCaseImpl(itemDataProvider);
	}

	@Bean
	public CalculateTotalOrderUseCaseImpl calculateTotalOrderUseCase() {
		return new CalculateTotalOrderUseCaseImpl();
	}

	@Bean
	public DeliverOrderUseCase deliverOrderUseCase(CreateOrUpdateOrderUseCase createOrUpdateOrderUseCase,
			RetrieveOrderUseCaseImpl retrieveOrderUseCase) {
		return new DeliverOrderUseCaseImpl(createOrUpdateOrderUseCase, retrieveOrderUseCase);
	}

	@Bean
	public DeleteItemUseCase deleteItemUseCase(ItemDataProvider itemDataProvider) {
		return new DeleteItemUseCaseImpl(itemDataProvider);
	}

	@Bean
	public EndOrderUseCase endOrderUseCase(CreateOrUpdateOrderUseCase createOrUpdateOrderUseCase,
			RetrieveOrderUseCaseImpl retrieveOrderUseCase // CreateQrCodeUseCase
															// createQrCodeUseCase
	) {
		return new EndOrderUseCaseImpl(createOrUpdateOrderUseCase, retrieveOrderUseCase // createQrCodeUseCase
		);
	}

	@Bean
	public CreateOrUpdateOrderUseCaseImpl createOrUpdateOrderUseCaseImpl(
			InitializeItemUseCase initializeItemUseCaseImpl, OrderDataProvider orderDataProvider,
			RestTemplate restTemplate, CalculateTotalOrderUseCaseImpl calculateTotalOrderUseCase) {
		return new CreateOrUpdateOrderUseCaseImpl(orderDataProvider, restTemplate, initializeItemUseCaseImpl,
				calculateTotalOrderUseCase);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
