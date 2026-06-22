package com.edumanage.notificaciones.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "intento_envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntentoEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El canal no puede estar vacio")
    @Column(nullable = false)
    private String canal;

    @NotBlank(message = "El estado no puede estar vacio")
    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_intento")
    private LocalDateTime fechaIntento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacion_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Notificacion notificacion;
}