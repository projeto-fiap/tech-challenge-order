package tech.fiap.project.infra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tech.fiap.project.app.dto.IngredientCategory;

import java.math.BigDecimal;

@Entity
@Table(name = "ingredient_item")
@Getter
@Setter
public class IngredientItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private String imageUrl;

	private BigDecimal price;

	private BigDecimal quantity;

	@Enumerated(EnumType.STRING)
	private IngredientCategory category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ItemEntity item;

	private String currency;

}
