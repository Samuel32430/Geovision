package com.example.geovision.serviceImpl;

import com.example.geovision.models.RecuperacionContrasena;
import com.example.geovision.repository.RecuperacionContrasenaRepository;
import com.example.geovision.service.RecuperacionContrasenaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecuperacionContrasenaServiceImpl implements RecuperacionContrasenaService {

    private final RecuperacionContrasenaRepository recuperacionContrasenaRepository;

    public RecuperacionContrasenaServiceImpl(RecuperacionContrasenaRepository recuperacionContrasenaRepository) {
        this.recuperacionContrasenaRepository = recuperacionContrasenaRepository;
    }

    @Override
    public RecuperacionContrasena save(RecuperacionContrasena entity) {
        return recuperacionContrasenaRepository.save(entity);
    }

    @Override
    public RecuperacionContrasena findById(Long id) {
        return recuperacionContrasenaRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        recuperacionContrasenaRepository.deleteById(id);
    }

    @Override
    public Optional<RecuperacionContrasena> read(Long id) {
        return recuperacionContrasenaRepository.findById(id);
    }

    @Override
    public Iterable<RecuperacionContrasena> findAll() {
        return recuperacionContrasenaRepository.findAll();
    }
}
