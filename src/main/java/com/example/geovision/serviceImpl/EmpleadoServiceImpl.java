package com.example.geovision.serviceImpl;

import com.example.geovision.models.Empleado;
import com.example.geovision.repository.EmpleadoRepository;
import com.example.geovision.service.EmpleadoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado save(Empleado entity) {
        return empleadoRepository.save(entity);
    }

    @Override
    public Empleado findById(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        empleadoRepository.deleteById(id);
    }

    @Override
    public Optional<Empleado> read(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Iterable<Empleado> findAll() {
        return empleadoRepository.findAll();
    }
}
