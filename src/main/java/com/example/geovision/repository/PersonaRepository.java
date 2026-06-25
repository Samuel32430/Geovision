package com.example.geovision.repository;

import com.example.geovision.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);
}
