package com.edumanage.inventario;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.inventario.model.Articulo;

class InventarioTest {

    Faker faker = new Faker();

    @Test
    void testCrearArticulo() {
        // Arrange
        Articulo articulo = new Articulo();
        articulo.setNombre(faker.commerce().productName());
        articulo.setCategoria(faker.commerce().department());
        articulo.setStockActual(faker.number().numberBetween(1, 100));

        // Act & Assert
        assertNotNull(articulo);
        assertNotNull(articulo.getNombre());
        assertNotNull(articulo.getCategoria());
        assertTrue(articulo.getStockActual() > 0);
    }
}