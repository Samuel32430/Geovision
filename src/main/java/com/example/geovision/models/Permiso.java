package com.example.geovision.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PERMISO")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPERMISO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDMODULO", nullable = false)
    private Modulo modulo;

    @Column(name = "NOMBREPERMISO", nullable = false, length = 100)
    private String nombrePermiso;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;
}
