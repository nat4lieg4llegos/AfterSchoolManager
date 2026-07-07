package com.edumanage.notificaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "notificacion")
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "notificacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<IntentoEnvio> intentos = new ArrayList<>();

    public void agregarIntento(IntentoEnvio i) {
        i.setNotificacion(this);
        this.intentos.add(i);
    }
}