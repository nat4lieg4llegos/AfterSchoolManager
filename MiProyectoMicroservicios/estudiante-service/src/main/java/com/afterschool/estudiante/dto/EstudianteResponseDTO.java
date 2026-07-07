package com.afterschool.estudiante.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstudianteResponseDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Long apoderadoId;
    private List<ContactoEmergenciaDTO> contactos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getApoderadoId() {
        return apoderadoId;
    }

    public void setApoderadoId(Long apoderadoId) {
        this.apoderadoId = apoderadoId;
    }

    public List<ContactoEmergenciaDTO> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoEmergenciaDTO> contactos) {
        this.contactos = contactos;
    }
}