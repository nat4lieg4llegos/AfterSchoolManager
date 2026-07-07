package com.edumanage.reportes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "detalle_reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El indicador no puede estar vacio")
    @Column(nullable = false)
    private String indicador;

    @NotNull(message = "El puntaje es obligatorio")
    @Min(value = 1, message = "El puntaje minimo es 1")
    @Max(value = 7, message = "El puntaje maximo es 7")
    @Column(nullable = false)
    private Integer puntaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporte_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private ReporteProgreso reporte;
}