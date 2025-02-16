package tech.fiap.project.app.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientItemDTO {

	private Long id;

	private String name;

	private String description;

	private String imageUrl;

	private BigDecimal price;

	private String currency;

	private IngredientCategory category;

	private BigDecimal quantity;

}
