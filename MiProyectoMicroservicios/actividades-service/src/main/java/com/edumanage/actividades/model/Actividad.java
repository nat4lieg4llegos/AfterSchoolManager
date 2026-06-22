package com.edumanage.actividades.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "actividad")
@NoArgsConstructor
@AllArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "taller_id")
    private Long tallerId;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotNull
    @Column(name = "fecha_programada")
    private LocalDate fechaProgramada;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MaterialRequerido> materiales = new ArrayList<>();

    public void agregarMaterial(MaterialRequerido m) {
        m.setActividad(this);
        this.materiales.add(m);
    }
}