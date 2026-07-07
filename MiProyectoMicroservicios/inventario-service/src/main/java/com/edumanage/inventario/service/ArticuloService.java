package com.edumanage.inventario.service;

import com.edumanage.inventario.model.Articulo;
import com.edumanage.inventario.model.MovimientoStock;
import com.edumanage.inventario.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
        validarMovimientos(articulo);
        // Conecta cada movimiento con su articulo (relacion bidireccional)
        if (articulo.getMovimientos() != null) {
            articulo.getMovimientos().forEach(m -> m.setArticulo(articulo));
        }
        return articuloRepository.save(articulo);
    }

    public Articulo actualizar(Long id, Articulo datos) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Articulo no encontrado con id " + id));
        articulo.setNombre(datos.getNombre());
        articulo.setCategoria(datos.getCategoria());
        articulo.setStockActual(datos.getStockActual());
        return articuloRepository.save(articulo);
    }

    public void eliminar(Long id) {
        if (!articuloRepository.existsById(id)) {
            throw new NoSuchElementException("Articulo no encontrado con id " + id);
        }
        articuloRepository.deleteById(id);
    }

    // Regla de negocio: una SALIDA no puede dejar el stock en negativo
    private void validarMovimientos(Articulo articulo) {
        if (articulo.getMovimientos() == null || articulo.getMovimientos().isEmpty()) {
            return;
        }
        int stockSimulado = articulo.getStockActual() != null ? articulo.getStockActual() : 0;
        for (MovimientoStock m : articulo.getMovimientos()) {
            if ("ENTRADA".equalsIgnoreCase(m.getTipo())) {
                stockSimulado += m.getCantidad();
            } else if ("SALIDA".equalsIgnoreCase(m.getTipo())) {
                stockSimulado -= m.getCantidad();
                if (stockSimulado < 0) {
                    throw new IllegalArgumentException(
                            "La salida de " + m.getCantidad() + " unidades deja el stock en negativo"
                    );
                }
            } else {
                throw new IllegalArgumentException(
                        "El tipo de movimiento debe ser ENTRADA o SALIDA, recibido: " + m.getTipo()
                );
            }
        }
    }
}