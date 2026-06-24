package com.example.geovision.serviceImpl;

import com.example.geovision.models.ExamenAgudezaVisual;
import com.example.geovision.repository.ExamenAgudezaVisualRepository;
import com.example.geovision.service.ExamenAgudezaVisualService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamenAgudezaVisualServiceImpl implements ExamenAgudezaVisualService {

    private final ExamenAgudezaVisualRepository examenAgudezaVisualRepository;

    public ExamenAgudezaVisualServiceImpl(ExamenAgudezaVisualRepository examenAgudezaVisualRepository) {
        this.examenAgudezaVisualRepository = examenAgudezaVisualRepository;
    }

    @Override
    public ExamenAgudezaVisual save(ExamenAgudezaVisual entity) {
        return examenAgudezaVisualRepository.save(entity);
    }

    @Override
    public ExamenAgudezaVisual findById(Long id) {
        return examenAgudezaVisualRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        examenAgudezaVisualRepository.deleteById(id);
    }

    @Override
    public Optional<ExamenAgudezaVisual> read(Long id) {
        return examenAgudezaVisualRepository.findById(id);
    }

    @Override
    public Iterable<ExamenAgudezaVisual> findAll() {
        return examenAgudezaVisualRepository.findAll();
    }

    @Override
    public List<ExamenAgudezaVisual> findByPacienteId(Long idPaciente) {
        return examenAgudezaVisualRepository.findByPacienteId(idPaciente);
    }

    @Override
    public List<ExamenAgudezaVisual> findByPacienteIdAndActivo(Long idPaciente) {
        return examenAgudezaVisualRepository.findByPacienteIdAndActivo(idPaciente);
    }
}
