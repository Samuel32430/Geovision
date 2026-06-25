package com.example.geovision.service;

import com.example.geovision.generic.CrudService;
import com.example.geovision.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService extends CrudService<Usuario, Long> {
    //Page<Usuario> findPage(String busqueda, Pageable pageable);
}
