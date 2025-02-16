package tech.fiap.project.infra.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
public class ItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private BigDecimal quantity;

	private String unit;

	private BigDecimal price;

	@Enumerated(EnumType.STRING)
	private ItemCategory itemCategory;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonIgnore // Ignora a serialização dos ingredientes
	private List<ItemEntity> ingredients;

	private String description;

	private String imageUrl;

}