package com.example.geovision.repository;

import com.example.geovision.models.RecetaOftalmicaInterna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaOftalmicaInternaRepository extends JpaRepository<RecetaOftalmicaInterna, Long> {
    boolean existsByExamenId(Long examenId);
}
