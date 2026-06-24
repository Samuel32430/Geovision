package com.example.geovision.security;

import com.example.geovision.models.Modulo;
import com.example.geovision.models.Usuario;
import com.example.geovision.repository.RolPermisoRepository;
import com.example.geovision.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolPermisoRepository rolPermisoRepository;

    @Override
    public UserDetails loadUserByUsername(String usuarioLogin) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuarioLogin(usuarioLogin)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + usuarioLogin));

        List<Modulo> modulosPermitidos = rolPermisoRepository.findByRol_Id(usuario.getRol().getId()).stream()
                .map(rolPermiso -> rolPermiso.getPermiso().getModulo())
                .distinct()
                .toList();

        return new UsuarioPrincipal(usuario, modulosPermitidos);
    }
}
