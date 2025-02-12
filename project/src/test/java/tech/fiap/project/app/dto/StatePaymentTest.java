package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatePaymentTest {

    @Test
    void testEnumValues() {

        assertEquals(2, StatePayment.values().length);
        assertEquals(StatePayment.ACCEPTED, StatePayment.valueOf("ACCEPTED"));
        assertEquals(StatePayment.REJECTED, StatePayment.valueOf("REJECTED"));
    }

    @Test
    void testEnumOrdinal() {

        assertEquals(0, StatePayment.ACCEPTED.ordinal());
        assertEquals(1, StatePayment.REJECTED.ordinal());
    }

    @Test
    void testInvalidEnumValue() {

        assertThrows(IllegalArgumentException.class, () -> StatePayment.valueOf("PENDING"));
    }
}
