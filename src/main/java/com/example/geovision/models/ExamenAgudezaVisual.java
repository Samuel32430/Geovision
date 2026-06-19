package com.example.geovision.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "EXAMEN_AGUDEZA_VISUAL")
public class ExamenAgudezaVisual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEXAMEN")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDPACIENTE", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "IDEMPLEADOREGISTRO", nullable = false)
    private Empleado empleadoRegistro;

    @Column(name = "ESFERAOD", nullable = false, precision = 5, scale = 2)
    private BigDecimal esferaOd;

    @Column(name = "CILINDROOD", nullable = false, precision = 5, scale = 2)
    private BigDecimal cilindroOd;

    @Column(name = "EJEOD", nullable = false)
    private Integer ejeOd;

    @Column(name = "ESFERAOI", nullable = false, precision = 5, scale = 2)
    private BigDecimal esferaOi;

    @Column(name = "CILINDROOI", nullable = false, precision = 5, scale = 2)
    private BigDecimal cilindroOi;

    @Column(name = "EJEOI", nullable = false)
    private Integer ejeOi;

    @Column(name = "DIP", nullable = false, precision = 5, scale = 2)
    private BigDecimal dip;

    @Column(name = "ADICION", precision = 5, scale = 2)
    private BigDecimal adicion;

    @Column(name = "MATERIALRECOMENDADO", length = 100)
    private String materialRecomendado;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;

    @Column(name = "FECHAEXAMEN", nullable = false)
    private LocalDate fechaExamen;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @Column(name = "FECHAMODIFICACION")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "IDEMPLEADOMODIFICA")
    private Empleado empleadoModifica;
}
