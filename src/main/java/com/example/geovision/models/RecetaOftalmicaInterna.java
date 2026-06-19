package com.example.geovision.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "RECETA_OFTALMICA_INTERNA")
public class RecetaOftalmicaInterna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRECETA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDPACIENTE", nullable = false)
    private Paciente paciente;

    @OneToOne
    @JoinColumn(name = "IDEXAMEN", nullable = false, unique = true)
    private ExamenAgudezaVisual examen;

    @ManyToOne
    @JoinColumn(name = "IDEMPLEADOEMISOR", nullable = false)
    private Empleado empleadoEmisor;

    @Column(name = "CODIGORECETA", nullable = false, unique = true, length = 20)
    private String codigoReceta;

    @Column(name = "FECHAEMISION", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @Column(name = "FECHAMODIFICACION")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "IDEMPLEADOMODIFICA")
    private Empleado empleadoModifica;
}
