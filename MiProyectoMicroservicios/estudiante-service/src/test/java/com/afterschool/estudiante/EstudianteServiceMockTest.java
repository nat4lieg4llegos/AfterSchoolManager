package com.afterschool.estudiante.service;

import com.afterschool.estudiante.dto.ContactoEmergenciaDTO;
import com.afterschool.estudiante.dto.EstudianteRequestDTO;
import com.afterschool.estudiante.exception.ContactoDuplicadoException;
import com.afterschool.estudiante.exception.EstudianteNoEncontradoException;
import com.afterschool.estudiante.model.Estudiante;
import com.afterschool.estudiante.repository.EstudianteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceMockTest {

    @Mock
    private EstudianteRepository repository;

    private EstudianteService estudianteService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        estudianteService = new EstudianteService(repository);
    }

    private ContactoEmergenciaDTO contacto(String nombre, String telefono) {
        ContactoEmergenciaDTO c = new ContactoEmergenciaDTO();
        c.setNombre(nombre);
        c.setParentesco("Madre");
        c.setTelefono(telefono);
        return c;
    }

    @Test
    void noDebePermitirDosContactosConElMismoTelefono() {
        // GIVEN: dos contactos con el mismo telefono en la misma solicitud de creacion
        EstudianteRequestDTO dto = new EstudianteRequestDTO();
        dto.setRut("11111111-1");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setFechaNacimiento(java.time.LocalDate.of(2015, 3, 10));

        List<ContactoEmergenciaDTO> contactos = new ArrayList<>();
        contactos.add(contacto("Maria Perez", "+56911111111"));
        contactos.add(contacto("Pedro Perez", "+56911111111")); // mismo telefono
        dto.setContactos(contactos);

        // WHEN + THEN
        ContactoDuplicadoException ex = assertThrows(
                ContactoDuplicadoException.class,
                () -> estudianteService.crear(dto)
        );
        assertTrue(ex.getMessage().contains("+56911111111"));

        // Nunca debe llegar a guardarse si la validacion fallo
        verify(repository, never()).save(any());
    }

    @Test
    void debeCrearEstudianteSiLosContactosTienenTelefonosDistintos() {
        EstudianteRequestDTO dto = new EstudianteRequestDTO();
        dto.setRut("22222222-2");
        dto.setNombre("Ana");
        dto.setApellido("Soto");
        dto.setFechaNacimiento(java.time.LocalDate.of(2016, 5, 20));

        List<ContactoEmergenciaDTO> contactos = new ArrayList<>();
        contactos.add(contacto("Contacto 1", "+56911111111"));
        contactos.add(contacto("Contacto 2", "+56922222222"));
        dto.setContactos(contactos);

        when(repository.save(any(Estudiante.class))).thenAnswer(inv -> inv.getArgument(0));

        assertDoesNotThrow(() -> estudianteService.crear(dto));
        verify(repository, times(1)).save(any(Estudiante.class));
    }

    @Test
    void noDebePermitirAgregarContactoConTelefonoYaExistente() {
        // GIVEN: un estudiante que ya tiene un contacto guardado con ese telefono
        Estudiante existente = new Estudiante();
        existente.setId(1L);
        var contactoExistente = new com.afterschool.estudiante.model.ContactoEmergencia();
        contactoExistente.setNombre("Contacto viejo");
        contactoExistente.setTelefono("+56933333333");
        existente.agregarContacto(contactoExistente);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));

        ContactoEmergenciaDTO nuevoContacto = contacto("Contacto nuevo", "+56933333333");

        assertThrows(
                ContactoDuplicadoException.class,
                () -> estudianteService.agregarContactoAEstudiante(1L, nuevoContacto)
        );
    }

    @Test
    void debeLanzarExcepcionSiElEstudianteNoExiste() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                EstudianteNoEncontradoException.class,
                () -> estudianteService.obtenerPorId(999L)
        );
    }
}
