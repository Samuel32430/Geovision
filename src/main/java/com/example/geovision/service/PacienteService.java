package com.example.geovision.service;

import com.example.geovision.generic.CrudService;
import com.example.geovision.models.Paciente;

import java.util.List;

public interface PacienteService extends CrudService<Paciente, Long> {
    List<Paciente> buscarPorNombreTelefonoODni(String busqueda);
    Paciente findByDni(String dni);
}
