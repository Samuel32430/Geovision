package com.example.geovision.serviceImpl;

import com.example.geovision.models.Permiso;
import com.example.geovision.repository.PermisoRepository;
import com.example.geovision.service.PermisoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;

    public PermisoServiceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    @Override
    public Permiso save(Permiso entity) {
        return permisoRepository.save(entity);
    }

    @Override
    public Permiso findById(Long id) {
        return permisoRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        permisoRepository.deleteById(id);
    }

    @Override
    public Optional<Permiso> read(Long id) {
        return permisoRepository.findById(id);
    }

    @Override
    public Iterable<Permiso> findAll() {
        return permisoRepository.findAll();
    }
}
