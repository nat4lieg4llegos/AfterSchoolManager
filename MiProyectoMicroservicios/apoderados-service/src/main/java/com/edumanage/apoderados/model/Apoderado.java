package com.edumanage.apoderados.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "apoderado")
public class Apoderado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String rut;

    @NotBlank
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @NotBlank
    private String correo;

    @NotBlank
    private String telefono;
}