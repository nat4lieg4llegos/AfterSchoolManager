package com.edumanage.tutores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String rut;

    @NotBlank
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @NotNull
    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;
}