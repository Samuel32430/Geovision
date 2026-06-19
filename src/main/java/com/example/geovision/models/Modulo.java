package com.example.geovision.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "MODULO")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMODULO")
    private Long id;

    @Column(name = "NOMBREMODULO", nullable = false, unique = true, length = 100)
    private String nombreModulo;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;
}
