package com.edumanage.apoderados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "persona_autorizada")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaAutorizada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @NotBlank(message = "El rut no puede estar vacio")
    @Column(nullable = false)
    private String rut;

    @NotBlank(message = "El parentesco no puede estar vacio")
    @Column(nullable = false)
    private String parentesco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apoderado_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Apoderado apoderado;
}