package tech.fiap.project.infra.mapper;

import tech.fiap.project.domain.entity.Order;
import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.app.dto.PaymentDTO;
import tech.fiap.project.domain.entity.OrderStatus;
import tech.fiap.project.infra.entity.ItemEntity;
import tech.fiap.project.infra.entity.OrderEntity;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OrderRepositoryMapper {

	private static final String PERSON_SERVICE_URL = "http://person-service/person/{id}";

	private static final String PAYMENT_SERVICE_URL = "http://payment-service/payment/{id}";

	private OrderRepositoryMapper() {
		// Private constructor to prevent instantiation
	}

	public static List<Order> toDomain(List<OrderEntity> orders) {
		return orders.stream().map(OrderRepositoryMapper::toDomain).collect(Collectors.toList());
	}

	public static List<OrderEntity> toEntity(List<Order> orders) {
		return orders.stream().map(OrderRepositoryMapper::toEntity).collect(Collectors.toList());
	}

	public static OrderEntity toEntity(Order order) {
		if (order == null) {
			return null;
		}

		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(order.getId());
		orderEntity.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
		orderEntity.setCreatedDate(order.getCreatedDate());
		orderEntity.setUpdatedDate(order.getUpdatedDate());
		orderEntity.setAwaitingTime(order.getAwaitingTime());
		orderEntity.setTotalPrice(order.getTotalPrice());

		if (order.getItems() != null) {
			orderEntity.setItems(
					order.getItems().stream().map(ItemRepositoryMapper::toEntity).collect(Collectors.toList()));
		}

		if (order.getPayments() != null) {
			orderEntity.setPaymentIds(order.getPayments().stream().map(PaymentDTO::getId).collect(Collectors.toList()));
		}

		if (order.getPerson() != null) {
			orderEntity.setPersonId(order.getPerson().getId());
		}

		return orderEntity;
	}

	public static Order toDomain(OrderEntity orderEntity) {
		if (orderEntity == null) {
			return null;
		}

		// Chamada HTTP para obter os pagamentos do microserviço
		List<PaymentDTO> payments = null;
		if (orderEntity.getPaymentIds() != null) {
			payments = orderEntity.getPaymentIds().stream().map(paymentId -> getPaymentFromService(paymentId))
					.collect(Collectors.toList());
		}

		List<ItemEntity> items = orderEntity.getItems();

		// Obtendo o status do pedido
		OrderStatus orderStatus = orderEntity.getStatus() != null
				? OrderStatus.valueOf(orderEntity.getStatus().toUpperCase()) : null;

		// Chamada HTTP para obter os dados da pessoa
		PersonDTO person = orderEntity.getPersonId() != null ? getPersonFromService(orderEntity.getPersonId()) : null;

		return new Order(orderEntity.getId(), orderStatus, orderEntity.getCreatedDate(), orderEntity.getUpdatedDate(),
				items.stream().map(ItemRepositoryMapper::toDomain).collect(Collectors.toList()), payments,
				orderEntity.getAwaitingTime(), person, orderEntity.getTotalPrice());
	}

	// Método para fazer a requisição HTTP para obter uma pessoa
	private static PersonDTO getPersonFromService(Long personId) {
		try {
			URL url = new URL(PERSON_SERVICE_URL.replace("{id}", personId.toString()));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return parsePersonResponse(response.toString());
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Método para fazer a requisição HTTP para obter o pagamento
	private static PaymentDTO getPaymentFromService(Long paymentId) {
		try {
			URL url = new URL(PAYMENT_SERVICE_URL.replace("{id}", paymentId.toString()));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return parsePaymentResponse(response.toString());
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Método para converter a resposta JSON para um objeto PersonDTO
	private static PersonDTO parsePersonResponse(String response) {
		// Aqui você pode usar Jackson ou Gson para mapear a resposta JSON para PersonDTO
		// Simulação de retorno
		return new PersonDTO(); // Substitua por código real de parsing
	}

	// Método para converter a resposta JSON para um objeto PaymentDTO
	private static PaymentDTO parsePaymentResponse(String response) {
		// Aqui você pode usar Jackson ou Gson para mapear a resposta JSON para PaymentDTO
		// Simulação de retorno
		return new PaymentDTO(); // Substitua por código real de parsing
	}

}
