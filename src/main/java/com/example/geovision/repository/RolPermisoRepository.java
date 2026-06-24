package com.example.geovision.repository;

import com.example.geovision.models.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {
    List<RolPermiso> findByRol_Id(Long idRol);
}
