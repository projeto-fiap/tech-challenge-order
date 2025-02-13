package tech.fiap.project.domain.entity;

import org.junit.jupiter.api.Test;
import tech.fiap.project.app.dto.IngredientCategory;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
class ItemTest {
    @Test
    void testIngredientItemCreation() {
        Currency currency = Currency.getInstance("USD");
        IngredientItem ingredientItem = new IngredientItem(
                "Tomato",
                "Fresh tomato slices",
                "http://example.com/tomato.jpg",
                new BigDecimal("1.50"),
                currency,
                IngredientCategory.ADDITIONAL,
                5L
        );

        assertEquals("Tomato", ingredientItem.getName());
        assertEquals("Fresh tomato slices", ingredientItem.getDescription());
        assertEquals("http://example.com/tomato.jpg", ingredientItem.getImageUrl());
        assertEquals(new BigDecimal("1.50"), ingredientItem.getPrice());
        assertEquals(currency, ingredientItem.getCurrency());
        assertEquals(IngredientCategory.ADDITIONAL, ingredientItem.getCategory());
        assertEquals(5L, ingredientItem.getQuantity());
    }

    @Test
    void testSetters() {
        IngredientItem ingredientItem = new IngredientItem(null, null, null, null, null, null, null);

        ingredientItem.setName("Lettuce");
        ingredientItem.setDescription("Crisp green lettuce");
        ingredientItem.setImageUrl("http://example.com/lettuce.jpg");
        ingredientItem.setPrice(new BigDecimal("0.99"));
        ingredientItem.setCurrency(Currency.getInstance("EUR"));
        ingredientItem.setCategory(IngredientCategory.NOT_PURCHASED);
        ingredientItem.setQuantity(10L);

        assertEquals("Lettuce", ingredientItem.getName());
        assertEquals("Crisp green lettuce", ingredientItem.getDescription());
        assertEquals("http://example.com/lettuce.jpg", ingredientItem.getImageUrl());
        assertEquals(new BigDecimal("0.99"), ingredientItem.getPrice());
        assertEquals(Currency.getInstance("EUR"), ingredientItem.getCurrency());
        assertEquals(IngredientCategory.NOT_PURCHASED, ingredientItem.getCategory());
        assertEquals(10L, ingredientItem.getQuantity());
    }
}