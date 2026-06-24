package com.example.geovision.service;

import com.example.geovision.generic.CrudService;
import com.example.geovision.models.RolPermiso;

import java.util.List;

public interface RolPermisoService extends CrudService<RolPermiso, Long> {
    List<RolPermiso> findByRolId(Long idRol);
}
