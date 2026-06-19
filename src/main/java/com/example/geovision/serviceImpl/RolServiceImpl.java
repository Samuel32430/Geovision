package com.example.geovision.serviceImpl;

import com.example.geovision.models.Rol;
import com.example.geovision.repository.RolRepository;
import com.example.geovision.service.RolService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol save(Rol entity) {
        return rolRepository.save(entity);
    }

    @Override
    public Rol findById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Optional<Rol> read(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Iterable<Rol> findAll() {
        return rolRepository.findAll();
    }
}
