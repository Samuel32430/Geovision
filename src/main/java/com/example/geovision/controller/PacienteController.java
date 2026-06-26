package com.example.geovision.controller;

import com.example.geovision.models.Paciente;
import com.example.geovision.models.Persona;
import com.example.geovision.security.UsuarioPrincipal;
import com.example.geovision.service.PacienteService;
import com.example.geovision.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final PersonaService personaService;

    @GetMapping("/pacientes")
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteService.findAll());
        return "pacientes/listar";
    }

    @GetMapping("/pacientes/nuevo")
    public String nuevoFormulario(Model model) {
        Paciente paciente = new Paciente();
        paciente.setPersona(new Persona());
        model.addAttribute("paciente", paciente);
        model.addAttribute("isReadonly", false);
        return "pacientes/formulario";
    }

    @GetMapping("/pacientes/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("paciente", pacienteService.findById(id));
        model.addAttribute("isReadonly", false);
        return "pacientes/formulario";
    }

    @GetMapping("/pacientes/{id}/ver")
    public String verPaciente(@PathVariable Long id, Model model) {
        model.addAttribute("paciente", pacienteService.findById(id));
        model.addAttribute("isReadonly", true);
        return "pacientes/formulario";
    }

    @PostMapping("/pacientes")
    public String crear(@ModelAttribute Paciente paciente, @AuthenticationPrincipal UsuarioPrincipal principal) {
        LocalDate hoy = LocalDate.now();

        Persona persona = paciente.getPersona();
        persona.setTipoPersona("paciente");
        persona.setFechaRegistro(hoy);
        persona = personaService.save(persona);

        paciente.setPersona(persona);
        paciente.setEstado("activo");
        paciente.setFechaRegistro(hoy);
        paciente.setEmpleadoModifica(principal.getUsuario().getEmpleado());
        pacienteService.save(paciente);
        return "redirect:/pacientes";
    }

    @PostMapping("/pacientes/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Paciente datosFormulario,
                              @AuthenticationPrincipal UsuarioPrincipal principal) {
        Paciente paciente = pacienteService.findById(id);

        Persona persona = paciente.getPersona();
        Persona datosPersona = datosFormulario.getPersona();
        persona.setNombres(datosPersona.getNombres());
        persona.setApellidos(datosPersona.getApellidos());
        persona.setDni(datosPersona.getDni());
        persona.setTelefono(datosPersona.getTelefono());
        persona.setCorreo(datosPersona.getCorreo());
        personaService.save(persona);

        paciente.setDireccion(datosFormulario.getDireccion());
        paciente.setFechaNacimiento(datosFormulario.getFechaNacimiento());
        paciente.setFechaModificacion(LocalDateTime.now());
        paciente.setEmpleadoModifica(principal.getUsuario().getEmpleado());
        pacienteService.save(paciente);
        return "redirect:/pacientes";
    }

    @PostMapping("/pacientes/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @AuthenticationPrincipal UsuarioPrincipal principal) {
        Paciente paciente = pacienteService.findById(id);
        paciente.setEstado("activo".equals(paciente.getEstado()) ? "inactivo" : "activo");
        paciente.setFechaModificacion(LocalDateTime.now());
        paciente.setEmpleadoModifica(principal.getUsuario().getEmpleado());
        pacienteService.save(paciente);
        return "redirect:/pacientes";
    }
}
