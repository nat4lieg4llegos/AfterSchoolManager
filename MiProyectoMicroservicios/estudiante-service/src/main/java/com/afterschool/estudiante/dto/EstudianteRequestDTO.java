package com.afterschool.estudiante.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
// ¡IMPORTANTE! Agrega estos dos nuevos imports para que reconozca las listas:
import java.util.ArrayList;
import java.util.List;

public class EstudianteRequestDTO {

    @NotBlank(message = "El rut es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    private Long apoderadoId;


    private List<ContactoEmergenciaDTO> contactos = new ArrayList<>();

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public Long getApoderadoId() { return apoderadoId; }
    public void setApoderadoId(Long apoderadoId) { this.apoderadoId = apoderadoId; }

    //  GETTER Y SETTER
    public List<ContactoEmergenciaDTO> getContactos() { return contactos; }
    public void setContactos(List<ContactoEmergenciaDTO> contactos) { this.contactos = contactos; }
}