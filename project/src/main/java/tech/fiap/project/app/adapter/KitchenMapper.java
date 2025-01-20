package tech.fiap.project.app.adapter;

import tech.fiap.project.app.dto.KitchenDTO;
import tech.fiap.project.domain.entity.Kitchen;

import java.util.List;

public class KitchenMapper {

	private KitchenMapper() {
	}

	public static List<KitchenDTO> toDTO(List<Kitchen> kitchens) {
		return kitchens.stream().map(KitchenMapper::toDTO).toList();
	}

	public static KitchenDTO toDTO(Kitchen kitchen) {
		if (kitchen == null) {
			return null;
		}
		else {
			KitchenDTO kitchenDTO = new KitchenDTO();
			kitchenDTO.setOrderId(kitchen.getOrderId());
			kitchenDTO.setCreationDate(kitchen.getCreationDate());
			kitchenDTO.setUpdatedDate(kitchen.getUpdatedDate());
			kitchenDTO.setStatus(kitchen.getStatus());
			return kitchenDTO;
		}
	}

	public static Kitchen toDomain(KitchenDTO kitchen) {
		if (kitchen == null) {
			return null;
		}
		else {
			return new Kitchen(kitchen.getOrderId(), kitchen.getCreationDate(), kitchen.getUpdatedDate(),
					kitchen.getStatus());
		}
	}

}
