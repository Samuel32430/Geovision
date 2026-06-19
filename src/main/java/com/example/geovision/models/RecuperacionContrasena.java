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
@Table(name = "RECUPERACION_CONTRASENA")
public class RecuperacionContrasena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRECUPERACION")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "CODIGORECUPERACION", nullable = false, length = 100)
    private String codigoRecuperacion;

    @Column(name = "FECHASOLICITUD", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;
}
