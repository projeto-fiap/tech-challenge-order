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
import tech.fiap.project.domain.usecase.CreateQrCodeUseCase;
import tech.fiap.project.domain.usecase.impl.CreateQrCodeUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.KitchenUseCaseImpl;
import tech.fiap.project.domain.usecase.impl.item.*;

import tech.fiap.project.domain.usecase.impl.order.*;

import tech.fiap.project.domain.usecase.item.DeleteItemUseCase;
import tech.fiap.project.domain.usecase.item.InitializeItemUseCase;
import tech.fiap.project.domain.usecase.order.CreateOrUpdateOrderUseCase;
import tech.fiap.project.domain.usecase.order.EndOrderUseCase;
import tech.fiap.project.domain.usecase.order.UpdateOrderUseCase;

import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@org.springframework.context.annotation.Configuration
@Getter
@ComponentScan("tech.fiap.project")
@Setter
public class Configuration {

	@Value("${keycloak.base-url}")
	String keycloakBaseUrl;

	@Value("${tech-challenge.order.client-id}")
	String orderClientId;

	@Value("${tech-challenge.order.client-secret}")
	String orderClientSecret;

	@Value("${tech-challenge.person.url}")
	String personUrl;

	@Value("${tech-challenge.payments.url}")
	String paymentsUrl;

	@Value("${tech-challenge.payments.client-id}")
	String paymentsClientId;

	@Value("${tech-challenge.payments.client-secret}")
	String paymentsClientSecret;

	@Value("${tech-challenge.kitchen.url}")
	String kitchenServiceUrl;

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
	public DeleteItemUseCase deleteItemUseCase(ItemDataProvider itemDataProvider) {
		return new DeleteItemUseCaseImpl(itemDataProvider);
	}

	@Bean
	public EndOrderUseCase endOrderUseCase(CreateOrUpdateOrderUseCase createOrUpdateOrderUseCase,
			RetrieveOrderUseCaseImpl retrieveOrderUseCase, CreateQrCodeUseCase createQrCodeUseCase) {
		return new EndOrderUseCaseImpl(createOrUpdateOrderUseCase, retrieveOrderUseCase, createQrCodeUseCase);
	}

	@Bean
	public CreateOrUpdateOrderUseCaseImpl createOrUpdateOrderUseCaseImpl(
			InitializeItemUseCase initializeItemUseCaseImpl, OrderDataProvider orderDataProvider,
			RestTemplate restTemplate, CalculateTotalOrderUseCaseImpl calculateTotalOrderUseCase) {
		return new CreateOrUpdateOrderUseCaseImpl(orderDataProvider, restTemplate, personUrl, initializeItemUseCaseImpl,
				calculateTotalOrderUseCase, orderClientId, orderClientSecret);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public UpdateOrderUseCase updateOrderUseCase(OrderDataProvider orderDataProvider) {
		return new UpdateOrderUseCaseImpl(orderDataProvider);
	}

	@Bean
	public CreateQrCodeUseCaseImpl createQrCodeUseCase(RestTemplate restTemplatePayments,
			RestTemplate restTemplateKeycloak) {
		return new CreateQrCodeUseCaseImpl(restTemplatePayments, restTemplateKeycloak, paymentsUrl, keycloakBaseUrl,
				paymentsClientId, paymentsClientSecret);
	}

	@Bean
	public KitchenUseCaseImpl kitchenUseCase(RestTemplate restTemplate) {
		return new KitchenUseCaseImpl(restTemplate, kitchenServiceUrl, paymentsClientId, keycloakBaseUrl,
				paymentsClientSecret);
	}

}
