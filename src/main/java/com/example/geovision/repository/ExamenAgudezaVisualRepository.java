package com.example.geovision.repository;

import com.example.geovision.models.ExamenAgudezaVisual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamenAgudezaVisualRepository extends JpaRepository<ExamenAgudezaVisual, Long> {
    
    @Query("SELECT e FROM ExamenAgudezaVisual e WHERE e.paciente.id = :idPaciente ORDER BY e.fechaExamen DESC")
    List<ExamenAgudezaVisual> findByPacienteId(@Param("idPaciente") Long idPaciente);
    
    @Query("SELECT e FROM ExamenAgudezaVisual e WHERE e.paciente.id = :idPaciente AND e.estado = 'activo' ORDER BY e.fechaExamen DESC")
    List<ExamenAgudezaVisual> findByPacienteIdAndActivo(@Param("idPaciente") Long idPaciente);
}
