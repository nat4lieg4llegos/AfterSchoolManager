package com.edumanage.cursos.service;

import com.edumanage.cursos.model.Horario;
import com.edumanage.cursos.model.Taller;
import com.edumanage.cursos.repository.TallerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TallerServiceMockTest {

    @Mock
    private TallerRepository tallerRepository;

    private TallerService tallerService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tallerService = new TallerService(tallerRepository);
    }

    private Horario horario(String dia, LocalTime inicio, LocalTime fin) {
        Horario h = new Horario();
        h.setDiaSemana(dia);
        h.setHoraInicio(inicio);
        h.setHoraFin(fin);
        return h;
    }

    @Test
    void noDebePermitirDosHorariosElMismoDiaYHora() {
        Taller taller = new Taller();
        taller.setNombre("Robotica Kids");
        taller.setSede("Sede Puente Alto");
        taller.setCupoMaximo(15);

        List<Horario> horarios = new ArrayList<>();
        horarios.add(horario("LUNES", LocalTime.of(16, 0), LocalTime.of(17, 30)));
        horarios.add(horario("LUNES", LocalTime.of(16, 0), LocalTime.of(18, 0))); // mismo dia y hora inicio

        taller.setHorarios(horarios);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> tallerService.guardar(taller)
        );
        assertTrue(ex.getMessage().contains("LUNES"));

        verify(tallerRepository, never()).save(any());
    }

    @Test
    void debePermitirHorariosDeDiasDistintos() {
        Taller taller = new Taller();
        taller.setNombre("Robotica Avanzada");
        taller.setSede("Campus Central");
        taller.setCupoMaximo(20);

        List<Horario> horarios = new ArrayList<>();
        horarios.add(horario("LUNES", LocalTime.of(16, 0), LocalTime.of(17, 30)));
        horarios.add(horario("MIERCOLES", LocalTime.of(16, 0), LocalTime.of(17, 30)));
        taller.setHorarios(horarios);

        when(tallerRepository.save(any(Taller.class))).thenReturn(taller);

        Taller resultado = tallerService.guardar(taller);

        assertNotNull(resultado);
        verify(tallerRepository, times(1)).save(taller);
    }

    @Test
    void debeLanzarExcepcionAlEliminarTallerInexistente() {
        when(tallerRepository.existsById(50L)).thenReturn(false);

        assertThrows(
                NoSuchElementException.class,
                () -> tallerService.eliminar(50L)
        );
    }
}
