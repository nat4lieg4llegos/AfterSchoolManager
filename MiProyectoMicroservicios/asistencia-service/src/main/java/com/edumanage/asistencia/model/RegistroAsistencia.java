package com.edumanage.asistencia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "registro_asistencia")
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "estudiante_id")
    private Long estudianteId;

    @NotNull
    @Column(name = "taller_id")
    private Long tallerId;

    @NotNull
    @Column(name = "fecha_hora_ingreso")
    private LocalDateTime fechaHoraIngreso;

    @Column(name = "fecha_hora_salida")
    private LocalDateTime fechaHoraSalida;

    @Column(name = "quien_retira")
    private String quienRetira;

    @OneToMany(mappedBy = "registro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ObservacionAsistencia> observaciones = new ArrayList<>();

    public void agregarObservacion(ObservacionAsistencia o) {
        o.setRegistro(this);
        this.observaciones.add(o);
    }
}