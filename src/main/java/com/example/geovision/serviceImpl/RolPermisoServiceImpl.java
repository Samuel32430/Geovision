package com.example.geovision.serviceImpl;

import com.example.geovision.models.RolPermiso;
import com.example.geovision.repository.RolPermisoRepository;
import com.example.geovision.service.RolPermisoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolPermisoServiceImpl implements RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;

    public RolPermisoServiceImpl(RolPermisoRepository rolPermisoRepository) {
        this.rolPermisoRepository = rolPermisoRepository;
    }

    @Override
    public RolPermiso save(RolPermiso entity) {
        return rolPermisoRepository.save(entity);
    }

    @Override
    public RolPermiso findById(Long id) {
        return rolPermisoRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        rolPermisoRepository.deleteById(id);
    }

    @Override
    public Optional<RolPermiso> read(Long id) {
        return rolPermisoRepository.findById(id);
    }

    @Override
    public Iterable<RolPermiso> findAll() {
        return rolPermisoRepository.findAll();
    }
}
