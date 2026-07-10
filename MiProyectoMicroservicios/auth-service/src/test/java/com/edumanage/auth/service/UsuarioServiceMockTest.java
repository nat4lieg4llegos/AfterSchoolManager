package com.edumanage.auth.service;

import com.edumanage.auth.model.Usuario;
import com.edumanage.auth.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceMockTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void noDebePermitirCrearUsuarioConEmailYaExistente() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Natalie");
        usuario.setEmail("natalie@afterschool.cl");
        usuario.setPassword("123456");
        usuario.setRol("ADMIN");

        when(usuarioRepository.existsByEmail("natalie@afterschool.cl")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.crear(usuario)
        );
        assertTrue(ex.getMessage().contains("natalie@afterschool.cl"));

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void debeCrearUsuarioSiElEmailNoExiste() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Jorge");
        usuario.setEmail("jorge@afterschool.cl");
        usuario.setPassword("123456");
        usuario.setRol("TUTOR");

        when(usuarioRepository.existsByEmail("jorge@afterschool.cl")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.crear(usuario);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void debeLanzarExcepcionAlActualizarUsuarioInexistente() {
        when(usuarioRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> usuarioService.actualizar(999L, new Usuario())
        );
    }
}
