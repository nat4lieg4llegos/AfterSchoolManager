package com.edumanage.notificaciones.NotificacionesTest.service;

import com.edumanage.notificaciones.NotificacionesTest.model.Articulo;
import com.edumanage.notificaciones.NotificacionesTest.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    public List<Articulo> obtenerTodos() {
        return articuloRepository.findAll();
    }

    public Optional<Articulo> obtenerPorId(Long id) {
        return articuloRepository.findById(id);
    }

    public Articulo crear(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    public Articulo actualizar(Long id, Articulo datos) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Articulo no encontrado"));
        articulo.setNombre(datos.getNombre());
        articulo.setCategoria(datos.getCategoria());
        articulo.setStockActual(datos.getStockActual());
        return articuloRepository.save(articulo);
    }

    public void eliminar(Long id) {
        articuloRepository.deleteById(id);
    }
}