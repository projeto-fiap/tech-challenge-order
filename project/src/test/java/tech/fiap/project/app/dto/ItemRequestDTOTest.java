package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRequestDTOTest {

    @Test
    void testItemRequestDTOConstructorAndGetters() {
        ItemRequestDTO dto = new ItemRequestDTO();
        dto.setId(1L);
        dto.setQuantity(BigDecimal.valueOf(2));
        dto.setUnit("unidade");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getQuantity()).isEqualTo(BigDecimal.valueOf(2));
        assertThat(dto.getUnit()).isEqualTo("unidade");
    }

    @Test
    void testNoArgsConstructor() {
        ItemRequestDTO dto = new ItemRequestDTO();
        assertThat(dto).isNotNull();
    }

    @Test
    void testSetters() {
        ItemRequestDTO dto = new ItemRequestDTO();
        dto.setId(2L);
        dto.setQuantity(BigDecimal.valueOf(3));
        dto.setUnit("litro");

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getQuantity()).isEqualTo(BigDecimal.valueOf(3));
        assertThat(dto.getUnit()).isEqualTo("litro");
    }
}
