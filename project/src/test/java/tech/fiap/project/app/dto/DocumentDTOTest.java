package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import tech.fiap.project.domain.entity.DocumentType;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentDTOTest {

    @Test
    void testDocumentDTOConstructorAndGetters() {
        DocumentDTO dto = new DocumentDTO(DocumentType.CPF, "123456789");

        assertThat(dto.getType()).isEqualTo(DocumentType.CPF);
        assertThat(dto.getValue()).isEqualTo("123456789");
    }

    @Test
    void testNoArgsConstructor() {
        DocumentDTO dto = new DocumentDTO();
        assertThat(dto).isNotNull();
    }

    @Test
    void testSetters() {
        DocumentDTO dto = new DocumentDTO();
        dto.setType(DocumentType.CPF);
        dto.setValue("987654321");

        assertThat(dto.getType()).isEqualTo(DocumentType.CPF);
        assertThat(dto.getValue()).isEqualTo("987654321");
    }
}
