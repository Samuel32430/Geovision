package com.example.geovision.service;

import com.example.geovision.generic.CrudService;
import com.example.geovision.models.Persona;

public interface PersonaService extends CrudService<Persona, Long> {
    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);
}
