package com.example.geovision.repository;

import com.example.geovision.models.ExamenAgudezaVisual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamenAgudezaVisualRepository extends JpaRepository<ExamenAgudezaVisual, Long> {
    Optional<ExamenAgudezaVisual> findFirstByPacienteIdOrderByIdDesc(Long idPaciente);
    List<ExamenAgudezaVisual> findAllByPacienteIdOrderByFechaExamenDesc(Long idPaciente);
}
