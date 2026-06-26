package com.example.geovision.serviceImpl;

import com.example.geovision.models.Persona;
import com.example.geovision.repository.PersonaRepository;
import com.example.geovision.service.PersonaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona save(Persona entity) {
        return personaRepository.save(entity);
    }

    @Override
    public Persona findById(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        personaRepository.deleteById(id);
    }

    @Override
    public Optional<Persona> read(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    public Iterable<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Override
    public boolean existsByDni(String dni) {
        return personaRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByDniAndIdNot(String dni, Long id) {
        return personaRepository.existsByDniAndIdNot(dni, id);
    }
}
