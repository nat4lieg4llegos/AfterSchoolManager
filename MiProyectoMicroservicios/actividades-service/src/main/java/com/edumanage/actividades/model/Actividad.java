package com.edumanage.actividades.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "actividad")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "taller_id")
    private Long tallerId;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotNull
    @Column(name = "fecha_programada")
    private LocalDate fechaProgramada;
}