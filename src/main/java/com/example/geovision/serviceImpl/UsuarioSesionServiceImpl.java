package com.example.geovision.serviceImpl;

import com.example.geovision.models.UsuarioSesion;
import com.example.geovision.repository.UsuarioSesionRepository;
import com.example.geovision.service.UsuarioSesionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioSesionServiceImpl implements UsuarioSesionService {

    private final UsuarioSesionRepository usuarioSesionRepository;

    public UsuarioSesionServiceImpl(UsuarioSesionRepository usuarioSesionRepository) {
        this.usuarioSesionRepository = usuarioSesionRepository;
    }

    @Override
    public UsuarioSesion save(UsuarioSesion entity) {
        return usuarioSesionRepository.save(entity);
    }

    @Override
    public UsuarioSesion findById(Long id) {
        return usuarioSesionRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        usuarioSesionRepository.deleteById(id);
    }

    @Override
    public Optional<UsuarioSesion> read(Long id) {
        return usuarioSesionRepository.findById(id);
    }

    @Override
    public Iterable<UsuarioSesion> findAll() {
        return usuarioSesionRepository.findAll();
    }
}
