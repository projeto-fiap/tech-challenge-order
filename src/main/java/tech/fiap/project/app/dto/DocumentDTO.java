package tech.fiap.project.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tech.fiap.project.domain.entity.DocumentType;

@Getter
@Setter
public class DocumentDTO {

	private DocumentType type;

	private String value;

	public DocumentDTO(DocumentType type, String value) {
		this.type = type;
		this.value = value;
	}

	public DocumentDTO() {

	}

}