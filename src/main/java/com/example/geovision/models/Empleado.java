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
@Table(name = "EMPLEADO")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEMPLEADO")
    private Long id;

    @OneToOne
    @JoinColumn(name = "IDPERSONA", nullable = false, unique = true)
    private Persona persona;

    @Column(name = "CARGO", nullable = false, length = 50)
    private String cargo;

    @Column(name = "FECHAINGRESO", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @Column(name = "FECHAMODIFICACION")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "IDEMPLEADOMODIFICA")
    private Empleado empleadoModifica;
}
