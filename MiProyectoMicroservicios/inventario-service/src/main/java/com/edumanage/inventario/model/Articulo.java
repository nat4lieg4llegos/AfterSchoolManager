package com.edumanage.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "articulo")
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String categoria;

    @NotNull
    @Column(name = "stock_actual")
    private Integer stockActual;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MovimientoStock> movimientos = new ArrayList<>();

    public void agregarMovimiento(MovimientoStock m) {
        m.setArticulo(this);
        this.movimientos.add(m);
    }
}