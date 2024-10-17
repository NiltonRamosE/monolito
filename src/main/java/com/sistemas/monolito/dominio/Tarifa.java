package com.sistemas.monolito.dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80)
    @NotBlank(message = "Debe ingresar una descripcion")
    private String descripcion;

    @Min(value = 0)
    private Double areaMinima;

    @NotNull
    @Min(value = 0)
    private Double precio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tarifa")
    private List<Orden> ordenes;
}