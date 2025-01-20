package tech.fiap.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.fiap.project.app.dto.PaymentDTO;
import tech.fiap.project.app.dto.PersonDTO;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Order {

	private Long id;

	private OrderStatus status;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private List<Item> items;

	private List<PaymentDTO> payments;

	private Duration awaitingTime;

	private PersonDTO person;

	private BigDecimal totalPrice;

}