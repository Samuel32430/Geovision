package com.example.geovision.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ROLPERMISO")
public class RolPermiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDROLPERMISO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDROL", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "IDPERMISO", nullable = false)
    private Permiso permiso;
}
