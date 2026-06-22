package com.edumanage.tutores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tutor")
@NoArgsConstructor
@AllArgsConstructor
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String rut;

    @NotBlank
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @NotNull
    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Especialidad> especialidades = new ArrayList<>();

    public void agregarEspecialidad(Especialidad e) {
        e.setTutor(this);
        this.especialidades.add(e);
    }
}