package com.edumanage.cursos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "taller")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La sede no puede estar vacía")
    @Column(nullable = false)
    private String sede;

    @NotNull(message = "El cupo máximo es obligatorio")
    @Min(value = 1, message = "El cupo mínimo es 1")
    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;
}