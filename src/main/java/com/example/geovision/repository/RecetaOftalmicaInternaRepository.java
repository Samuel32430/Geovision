package com.example.geovision.repository;

import com.example.geovision.models.RecetaOftalmicaInterna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecetaOftalmicaInternaRepository extends JpaRepository<RecetaOftalmicaInterna, Long> {
    
    @Query("SELECT r FROM RecetaOftalmicaInterna r WHERE r.paciente.id = :idPaciente ORDER BY r.fechaEmision DESC")
    List<RecetaOftalmicaInterna> findByPacienteId(@Param("idPaciente") Long idPaciente);
    
    @Query("SELECT r FROM RecetaOftalmicaInterna r WHERE r.paciente.id = :idPaciente AND r.estado = 'generada' ORDER BY r.fechaEmision DESC")
    List<RecetaOftalmicaInterna> findByPacienteIdAndGenerada(@Param("idPaciente") Long idPaciente);
    
    @Query("SELECT r FROM RecetaOftalmicaInterna r WHERE r.examen.id = :idExamen")
    Optional<RecetaOftalmicaInterna> findByExamenId(@Param("idExamen") Long idExamen);
    
    boolean existsByCodigoReceta(String codigoReceta);
}
