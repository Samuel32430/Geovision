package com.example.geovision.serviceImpl;

import com.example.geovision.models.Paciente;
import com.example.geovision.repository.PacienteRepository;
import com.example.geovision.service.PacienteService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente save(Paciente entity) {
        return pacienteRepository.save(entity);
    }

    @Override
    public Paciente findById(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public Optional<Paciente> read(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Iterable<Paciente> findAll() {
        return pacienteRepository.findAll();
    }
}
