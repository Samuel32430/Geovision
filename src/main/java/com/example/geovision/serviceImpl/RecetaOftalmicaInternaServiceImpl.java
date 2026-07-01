package com.example.geovision.serviceImpl;

import com.example.geovision.models.RecetaOftalmicaInterna;
import com.example.geovision.repository.RecetaOftalmicaInternaRepository;
import com.example.geovision.service.RecetaOftalmicaInternaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecetaOftalmicaInternaServiceImpl implements RecetaOftalmicaInternaService {

    private final RecetaOftalmicaInternaRepository recetaOftalmicaInternaRepository;

    public RecetaOftalmicaInternaServiceImpl(RecetaOftalmicaInternaRepository recetaOftalmicaInternaRepository) {
        this.recetaOftalmicaInternaRepository = recetaOftalmicaInternaRepository;
    }

    @Override
    public RecetaOftalmicaInterna save(RecetaOftalmicaInterna entity) {
        return recetaOftalmicaInternaRepository.save(entity);
    }

    @Override
    public RecetaOftalmicaInterna findById(Long id) {
        return recetaOftalmicaInternaRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        recetaOftalmicaInternaRepository.deleteById(id);
    }

    @Override
    public Optional<RecetaOftalmicaInterna> read(Long id) {
        return recetaOftalmicaInternaRepository.findById(id);
    }

    @Override
    public Iterable<RecetaOftalmicaInterna> findAll() {
        return recetaOftalmicaInternaRepository.findAll();
    }

    @Override
    public boolean existsByExamenId(Long examenId) {
        return recetaOftalmicaInternaRepository.existsByExamenId(examenId);
    }
}
