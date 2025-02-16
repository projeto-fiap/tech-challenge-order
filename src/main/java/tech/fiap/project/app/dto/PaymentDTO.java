package tech.fiap.project.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
public class PaymentDTO {

	private Long id;

	private LocalDateTime paymentDate;

	private String paymentMethod;

	private BigDecimal amount;

	private Currency currency;

	private StatePayment state;

	private OrderResponsePaymentDTO order;

}
