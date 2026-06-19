package com.example.geovision.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USUARIO_SESION")
public class UsuarioSesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSESION")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "TOKENSESION", nullable = false, unique = true, length = 500)
    private String tokenSesion;

    @Column(name = "FECHAINICIO", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "FECHACIERRE")
    private LocalDateTime fechaCierre;

    @Column(name = "IPACCESO", nullable = false, length = 45)
    private String ipAcceso;

    @Column(name = "ESTADOSESION", nullable = false, length = 10)
    private String estadoSesion;
}
