package com.edumanage.asistencia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registro_asistencia")
public class RegistroAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "estudiante_id")
    private Long estudianteId;

    @NotNull
    @Column(name = "taller_id")
    private Long tallerId;

    @NotNull
    @Column(name = "fecha_hora_ingreso")
    private LocalDateTime fechaHoraIngreso;

    @Column(name = "fecha_hora_salida")
    private LocalDateTime fechaHoraSalida;

    @Column(name = "quien_retira")
    private String quienRetira;
}