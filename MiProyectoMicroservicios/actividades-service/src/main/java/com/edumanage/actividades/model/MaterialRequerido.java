package com.edumanage.actividades.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "material_requerido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequerido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del material no puede estar vacio")
    @Column(name = "nombre_material", nullable = false)
    private String nombreMaterial;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad necesaria debe ser al menos 1")
    @Column(name = "cantidad_necesaria", nullable = false)
    private Integer cantidadNecesaria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Actividad actividad;
}