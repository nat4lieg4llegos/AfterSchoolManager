package com.edumanage.reportes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reporte_progreso")
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DetalleReporte> detalles = new ArrayList<>();

    public void agregarDetalle(DetalleReporte d) {
        d.setReporte(this);
        this.detalles.add(d);
    }
}