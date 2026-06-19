package com.example.geovision.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ROL")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDROL")
    private Long id;

    @Column(name = "NOMBREROL", nullable = false, unique = true, length = 100)
    private String nombreRol;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;
}
