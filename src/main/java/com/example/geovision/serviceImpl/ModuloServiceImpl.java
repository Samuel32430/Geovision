package com.example.geovision.serviceImpl;

import com.example.geovision.models.Modulo;
import com.example.geovision.repository.ModuloRepository;
import com.example.geovision.service.ModuloService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModuloServiceImpl implements ModuloService {

    private final ModuloRepository moduloRepository;

    public ModuloServiceImpl(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    @Override
    public Modulo save(Modulo entity) {
        return moduloRepository.save(entity);
    }

    @Override
    public Modulo findById(Long id) {
        return moduloRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        moduloRepository.deleteById(id);
    }

    @Override
    public Optional<Modulo> read(Long id) {
        return moduloRepository.findById(id);
    }

    @Override
    public Iterable<Modulo> findAll() {
        return moduloRepository.findAll();
    }
}
