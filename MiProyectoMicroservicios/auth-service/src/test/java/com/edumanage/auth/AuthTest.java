package com.edumanage.auth;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.auth.model.Usuario;

class AuthTest {

    Faker faker = new Faker();

    @Test
    void testCrearUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre(faker.name().fullName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.internet().password());
        usuario.setRol("ADMIN");

        // Act & Assert
        assertNotNull(usuario);
        assertNotNull(usuario.getNombre());
        assertNotNull(usuario.getEmail());
        assertTrue(usuario.getEmail().contains("@"));
        assertNotNull(usuario.getRol());
    }
}