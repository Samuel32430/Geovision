package com.example.geovision.service;

import com.example.geovision.generic.CrudService;
import com.example.geovision.models.ExamenAgudezaVisual;

import java.util.List;
import java.util.Optional;

public interface ExamenAgudezaVisualService extends CrudService<ExamenAgudezaVisual, Long> {
    Optional<ExamenAgudezaVisual> getLatestExamenByPaciente(Long idPaciente);
    List<ExamenAgudezaVisual> getHistorialByPaciente(Long idPaciente);
}
