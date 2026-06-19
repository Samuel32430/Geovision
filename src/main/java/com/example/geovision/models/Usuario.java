package com.example.geovision.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSUARIO")
    private Long id;

    @OneToOne
    @JoinColumn(name = "IDEMPLEADO", nullable = false, unique = true)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "IDROL", nullable = false)
    private Rol rol;

    @Column(name = "USUARIOLOGIN", nullable = false, unique = true, length = 50)
    private String usuarioLogin;

    @Column(name = "CONTRASENAHASH", nullable = false, length = 255)
    private String contrasenaHash;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @Column(name = "FECHAREGISTRO", nullable = false)
    private LocalDate fechaRegistro;
}
