package com.edumanage.auth.service;

import com.edumanage.auth.model.Usuario;
import com.edumanage.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crear(Usuario usuario) {
        // Regla de negocio: el email no puede estar repetido
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email " + usuario.getEmail());
        }
        // Conecta cada sesion con su usuario (relacion bidireccional)
        if (usuario.getSesiones() != null) {
            usuario.getSesiones().forEach(s -> s.setUsuario(usuario));
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario datos) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con id " + id));
        usuario.setNombre(datos.getNombre());
        usuario.setEmail(datos.getEmail());
        usuario.setPassword(datos.getPassword());
        usuario.setRol(datos.getRol());
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NoSuchElementException("Usuario no encontrado con id " + id);
        }
        usuarioRepository.deleteById(id);
    }
}