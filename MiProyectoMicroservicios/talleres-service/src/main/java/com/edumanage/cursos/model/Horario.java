package com.edumanage.cursos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El taller es obligatorio")
    @ManyToOne
    @JoinColumn(name = "taller_id", nullable = false)
    private Taller taller;

    @NotBlank(message = "El día no puede estar vacío")
    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;
}