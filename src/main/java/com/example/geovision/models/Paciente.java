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
@Table(name = "PACIENTE")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPACIENTE")
    private Long id;

    @OneToOne
    @JoinColumn(name = "IDPERSONA", nullable = false, unique = true)
    private Persona persona;

    @Column(name = "DIRECCION", nullable = false, length = 300)
    private String direccion;

    @Column(name = "FECHANACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @Column(name = "FECHAREGISTRO", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "FECHAMODIFICACION")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "IDEMPLEADOMODIFICA")
    private Empleado empleadoModifica;
}
