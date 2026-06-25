package com.example.geovision.repository;

import com.example.geovision.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuarioLogin(String usuarioLogin);

    /*Page<Usuario> findByUsuarioLoginContainingIgnoreCaseOrRol_NombreRolContainingIgnoreCase(
            String usuario,
            String rol,
            Pageable pageable
    );*/
}
