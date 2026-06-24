package com.example.geovision.service;

import com.example.geovision.generic.CrudService;
import com.example.geovision.models.ExamenAgudezaVisual;

import java.util.List;

public interface ExamenAgudezaVisualService extends CrudService<ExamenAgudezaVisual, Long> {
    List<ExamenAgudezaVisual> findByPacienteId(Long idPaciente);
    List<ExamenAgudezaVisual> findByPacienteIdAndActivo(Long idPaciente);
}
