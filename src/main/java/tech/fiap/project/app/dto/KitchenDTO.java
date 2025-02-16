package tech.fiap.project.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tech.fiap.project.domain.entity.KitchenStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class KitchenDTO {

	private Long orderId;

	private LocalDateTime creationDate;

	private LocalDateTime updatedDate;

	private KitchenStatus status;

}
