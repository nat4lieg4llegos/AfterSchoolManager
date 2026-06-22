package com.afterschool.estudiante.dto;

import jakarta.validation.constraints.NotBlank;

public class ContactoEmergenciaDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El parentesco es obligatorio")
    private String parentesco;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    // Getters y Setters manuales (respetando el estilo de tu servicio)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}