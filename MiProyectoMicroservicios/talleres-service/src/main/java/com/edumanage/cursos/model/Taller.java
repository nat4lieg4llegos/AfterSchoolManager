package com.edumanage.cursos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "taller")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La sede no puede estar vacia")
    @Column(nullable = false)
    private String sede;

    @NotNull(message = "El cupo maximo es obligatorio")
    @Min(value = 1, message = "El cupo minimo es 1")
    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;

    @OneToMany(mappedBy = "taller", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Horario> horarios = new ArrayList<>();

    public void agregarHorario(Horario h) {
        h.setTaller(this);
        this.horarios.add(h);
    }
}