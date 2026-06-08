package com.edumanage.notificaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "apoderado_id")
    private Long apoderadoId;

    @NotBlank
    private String tipo;

    @NotBlank
    private String mensaje;

    @NotNull
    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    private Boolean leida = false;
}