package com.example.geovision.controller;

import com.example.geovision.models.ExamenAgudezaVisual;
import com.example.geovision.models.Paciente;
import com.example.geovision.models.Persona;
import com.example.geovision.service.ExamenAgudezaVisualService;
import com.example.geovision.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.geovision.service.PersonaService;

@Controller
@RequestMapping("/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final PacienteService pacienteService;
    private final ExamenAgudezaVisualService examenService;
    private final PersonaService personaService;

    @GetMapping
    public String verHistorial(@RequestParam(required = false) Long idPaciente, Model model) {
        // We load all patients to power the autocomplete/modal search
        model.addAttribute("pacientes", pacienteService.findAll());

        if (idPaciente != null) {
            Paciente paciente = pacienteService.findById(idPaciente);
            if (paciente != null) {
                model.addAttribute("pacienteSeleccionado", paciente);
                List<ExamenAgudezaVisual> examenes = examenService.getHistorialByPaciente(idPaciente);
                model.addAttribute("examenes", examenes);
            } else {
                model.addAttribute("error", "Paciente no encontrado.");
            }
        }

        return "historial/vista";
    }

    @PostMapping("/editar-paciente")
    public String editarPaciente(@RequestParam("idPaciente") Long idPaciente,
                                 @RequestParam("nombres") String nombres,
                                 @RequestParam("apellidos") String apellidos,
                                 @RequestParam("dni") String dni,
                                 @RequestParam("telefono") String telefono) {
        Paciente paciente = pacienteService.findById(idPaciente);
        if (paciente != null && paciente.getPersona() != null) {
            Persona persona = paciente.getPersona();
            persona.setNombres(nombres);
            persona.setApellidos(apellidos);
            persona.setDni(dni);
            persona.setTelefono(telefono);
            personaService.save(persona);
            pacienteService.save(paciente);
        }
        return "redirect:/historial?idPaciente=" + idPaciente;
    }
}
