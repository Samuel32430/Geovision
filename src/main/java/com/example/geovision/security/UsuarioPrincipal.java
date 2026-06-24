package com.example.geovision.security;

import com.example.geovision.models.Modulo;
import com.example.geovision.models.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Stream;

public class UsuarioPrincipal implements UserDetails {

    private final Usuario usuario;
    private final List<Modulo> modulosPermitidos;

    public UsuarioPrincipal(Usuario usuario, List<Modulo> modulosPermitidos) {
        this.usuario = usuario;
        this.modulosPermitidos = modulosPermitidos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        Stream<GrantedAuthority> rolAuthority = Stream.of(
                new SimpleGrantedAuthority(AuthorityUtil.toRoleAuthority(usuario.getRol().getNombreRol())));
        Stream<GrantedAuthority> moduloAuthorities = modulosPermitidos.stream()
                .map(modulo -> new SimpleGrantedAuthority(AuthorityUtil.toModuloAuthority(modulo.getNombreModulo())));
        return Stream.concat(rolAuthority, moduloAuthorities).toList();
    }

    @Override
    public String getPassword() {
        return usuario.getContrasenaHash();
    }

    @Override
    public String getUsername() {
        return usuario.getUsuarioLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "activo".equals(usuario.getEstado());
    }
}
