package tech.fiap.project.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTypeTest {
    @Test
    void testDocumentTypeEnum() {
        // Verifica se a enumeração contém o valor esperado
        assertNotNull(DocumentType.valueOf("CPF"));

        // Verifica se a quantidade de valores está correta
        DocumentType[] values = DocumentType.values();
        assertEquals(1, values.length);

        // Verifica se o primeiro valor é o esperado
        assertEquals(DocumentType.CPF, values[0]);
    }
}

