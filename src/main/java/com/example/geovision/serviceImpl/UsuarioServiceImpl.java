package com.example.geovision.serviceImpl;

import com.example.geovision.models.Usuario;
import com.example.geovision.repository.UsuarioRepository;
import com.example.geovision.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario save(Usuario entity) {
        return usuarioRepository.save(entity);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> read(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Iterable<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> findPage(String busqueda, Pageable pageable) {
        if (busqueda == null || busqueda.isBlank()) {
            return usuarioRepository.findAll(pageable);
        }
        return usuarioRepository.findByUsuarioLoginContainingIgnoreCaseOrRol_NombreRolContainingIgnoreCase(
                busqueda,
                busqueda,
                pageable
        );
    }

    @Override
    public boolean existsByUsuarioLogin(String usuarioLogin) {
        return usuarioRepository.existsByUsuarioLogin(usuarioLogin);
    }

    @Override
    public boolean existsByUsuarioLoginAndIdNot(String usuarioLogin, Long id) {
        return usuarioRepository.existsByUsuarioLoginAndIdNot(usuarioLogin, id);
    }
}
