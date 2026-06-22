package com.edumanage.asistencia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "observacion_asistencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservacionAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo no puede estar vacio")
    @Column(nullable = false)
    private String tipo;

    @NotBlank(message = "La descripcion no puede estar vacia")
    @Column(nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private RegistroAsistencia registro;
}