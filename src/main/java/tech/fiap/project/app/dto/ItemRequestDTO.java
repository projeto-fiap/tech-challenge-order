package tech.fiap.project.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemRequestDTO {

	private Long id;

	private BigDecimal quantity;

	private String unit;

}
