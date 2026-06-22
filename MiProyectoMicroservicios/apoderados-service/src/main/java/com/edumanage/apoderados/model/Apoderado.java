package com.edumanage.apoderados.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "apoderado")
@NoArgsConstructor
@AllArgsConstructor
public class Apoderado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String rut;

    @NotBlank
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @NotBlank
    private String correo;

    @NotBlank
    private String telefono;

    @OneToMany(mappedBy = "apoderado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<PersonaAutorizada> personasAutorizadas = new ArrayList<>();

    public void agregarPersonaAutorizada(PersonaAutorizada p) {
        p.setApoderado(this);
        this.personasAutorizadas.add(p);
    }
}