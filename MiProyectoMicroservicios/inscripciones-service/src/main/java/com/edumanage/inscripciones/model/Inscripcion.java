package com.edumanage.inscripciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscripcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El estudiante_id es obligatorio")
    @Column(name = "estudiante_id", nullable = false)
    private Long estudianteId;

    @NotNull(message = "El taller_id es obligatorio")
    @Column(name = "taller_id", nullable = false)
    private Long tallerId;

    @NotNull(message = "La fecha de inscripción es obligatoria")
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    private String estado;
}