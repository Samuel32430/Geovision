package com.example.geovision.controller;

import com.example.geovision.models.Paciente;
import com.example.geovision.models.Persona;
import com.example.geovision.security.UsuarioPrincipal;
import com.example.geovision.service.PacienteService;
import com.example.geovision.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return "pacientes/formulario";
    }

    @GetMapping("/pacientes/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("paciente", pacienteService.findById(id));
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

    /**
     * CU-AT01: Registrar Paciente - Verificar si existe por DNI (RN-AT03)
     * Endpoint API para verificar duplicidad antes de registrar
     */
    @GetMapping("/api/pacientes/verificar-dni")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verificarDni(@RequestParam String dni) {
        Map<String, Object> response = new HashMap<>();
        
        if (dni == null || dni.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "El DNI es obligatorio");
            return ResponseEntity.badRequest().body(response);
        }

        Paciente pacienteExistente = pacienteService.findByDni(dni);
        
        if (pacienteExistente != null) {
            response.put("success", true);
            response.put("existe", true);
            response.put("message", "Ya existe un paciente registrado con este DNI");
            response.put("idPaciente", pacienteExistente.getId());
            response.put("nombres", pacienteExistente.getPersona().getNombres());
            response.put("apellidos", pacienteExistente.getPersona().getApellidos());
        } else {
            response.put("success", true);
            response.put("existe", false);
            response.put("message", "DNI disponible para registro");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * CU-AT02: Consultar Historial del Paciente - Buscar pacientes
     * RF-AT03: Buscar pacientes por nombre, teléfono o DNI
     */
    @GetMapping("/api/pacientes/buscar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> buscarPacientes(@RequestParam(required = false) String busqueda) {
        Map<String, Object> response = new HashMap<>();
        
        if (busqueda == null || busqueda.trim().isEmpty()) {
            List<Paciente> todos = (List<Paciente>) pacienteService.findAll();
            response.put("success", true);
            response.put("data", todos);
            response.put("message", "Se listan todos los pacientes");
        } else {
            List<Paciente> resultados = pacienteService.buscarPorNombreTelefonoODni(busqueda.trim());
            response.put("success", true);
            response.put("data", resultados);
            response.put("message", "Se encontraron " + resultados.size() + " paciente(s)");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * CU-AT02: Consultar Historial del Paciente - Obtener detalle completo
     * RF-AT05 a RF-AT10: Mostrar datos personales, atenciones, recetas, medidas, material y observaciones
     */
    @GetMapping("/api/pacientes/{id}/historial")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerHistorial(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        Paciente paciente = pacienteService.findById(id);
        
        if (paciente == null) {
            response.put("success", false);
            response.put("message", "Paciente no encontrado");
            return ResponseEntity.notFound().build();
        }

        response.put("success", true);
        response.put("paciente", paciente);
        response.put("persona", paciente.getPersona());
        // El historial completo se arma en el controller de examen/receta
        response.put("message", "Datos del paciente obtenidos correctamente");

        return ResponseEntity.ok(response);
    }
}
