package com.edumanage.inventario.service;

import com.edumanage.inventario.model.Articulo;
import com.edumanage.inventario.model.MovimientoStock;
import com.edumanage.inventario.repository.ArticuloRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticuloServiceMockTest {

    @Mock
    private ArticuloRepository articuloRepository;

    // @InjectMocks crea el ArticuloService real e inyecta el mock de arriba
    // en su campo @Autowired (ArticuloService usa inyeccion por campo, no por constructor)
    @InjectMocks
    private ArticuloService articuloService;

    @Test
    void noDebePermitirSalidaQueDejeElStockNegativo() {
        // GIVEN: un articulo con stock 5, y una SALIDA de 10 unidades (deja el stock en -5)
        Articulo articulo = new Articulo();
        articulo.setNombre("Cartulinas");
        articulo.setCategoria("Materiales");
        articulo.setStockActual(5);

        MovimientoStock salida = new MovimientoStock();
        salida.setTipo("SALIDA");
        salida.setCantidad(10);

        List<MovimientoStock> movimientos = new ArrayList<>();
        movimientos.add(salida);
        articulo.setMovimientos(movimientos);

        // WHEN + THEN
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> articuloService.crear(articulo)
        );
        assertTrue(ex.getMessage().contains("negativo"));

        // Como la validacion fallo, nunca debe llegar a guardarse
        verify(articuloRepository, never()).save(any());
    }

    @Test
    void debePermitirSalidaQueNoDejeElStockNegativo() {
        // GIVEN: stock 10, salida de 4 (queda en 6, valido)
        Articulo articulo = new Articulo();
        articulo.setNombre("Lapices");
        articulo.setCategoria("Materiales");
        articulo.setStockActual(10);

        MovimientoStock salida = new MovimientoStock();
        salida.setTipo("SALIDA");
        salida.setCantidad(4);

        List<MovimientoStock> movimientos = new ArrayList<>();
        movimientos.add(salida);
        articulo.setMovimientos(movimientos);

        when(articuloRepository.save(any(Articulo.class))).thenReturn(articulo);

        // WHEN
        Articulo resultado = articuloService.crear(articulo);

        // THEN
        assertNotNull(resultado);
        verify(articuloRepository, times(1)).save(articulo);
    }

    @Test
    void noDebePermitirTipoDeMovimientoInvalido() {
        Articulo articulo = new Articulo();
        articulo.setStockActual(10);

        MovimientoStock movimientoRaro = new MovimientoStock();
        movimientoRaro.setTipo("TRASPASO"); // ni ENTRADA ni SALIDA
        movimientoRaro.setCantidad(1);

        List<MovimientoStock> movimientos = new ArrayList<>();
        movimientos.add(movimientoRaro);
        articulo.setMovimientos(movimientos);

        assertThrows(
                IllegalArgumentException.class,
                () -> articuloService.crear(articulo)
        );
    }

    @Test
    void debeLanzarExcepcionAlActualizarArticuloInexistente() {
        when(articuloRepository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> articuloService.actualizar(50L, new Articulo())
        );
    }
}
