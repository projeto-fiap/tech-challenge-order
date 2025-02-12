package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import tech.fiap.project.domain.entity.KitchenStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class KitchenDTOTest {

    @Test
    void testKitchenDTOConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        KitchenDTO dto = new KitchenDTO();
        dto.setOrderId(1L);
        dto.setCreationDate(now);
        dto.setUpdatedDate(now);
        dto.setStatus(KitchenStatus.IN_PRODUCTION);

        assertThat(dto.getOrderId()).isEqualTo(1L);
        assertThat(dto.getCreationDate()).isEqualTo(now);
        assertThat(dto.getUpdatedDate()).isEqualTo(now);
        assertThat(dto.getStatus()).isEqualTo(KitchenStatus.IN_PRODUCTION);
    }

    @Test
    void testNoArgsConstructor() {
        KitchenDTO dto = new KitchenDTO();
        assertThat(dto).isNotNull();
    }

    @Test
    void testSetters() {
        KitchenDTO dto = new KitchenDTO();
        LocalDateTime creationDate = LocalDateTime.of(2024, 2, 10, 12, 0);
        LocalDateTime updatedDate = LocalDateTime.of(2024, 2, 10, 14, 0);

        dto.setOrderId(2L);
        dto.setCreationDate(creationDate);
        dto.setUpdatedDate(updatedDate);
        dto.setStatus(KitchenStatus.DONE);

        assertThat(dto.getOrderId()).isEqualTo(2L);
        assertThat(dto.getCreationDate()).isEqualTo(creationDate);
        assertThat(dto.getUpdatedDate()).isEqualTo(updatedDate);
        assertThat(dto.getStatus()).isEqualTo(KitchenStatus.DONE);
    }
}
