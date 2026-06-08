package com.edumanage.reportes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "reporte_progreso")
public class ReporteProgreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "estudiante_id")
    private Long estudianteId;

    @NotNull
    @Column(name = "taller_id")
    private Long tallerId;

    @NotBlank
    private String observaciones;

    @NotNull
    @Column(name = "fecha_reporte")
    private LocalDate fechaReporte;
}