package com.example.geovision.controller;

import com.example.geovision.models.ExamenAgudezaVisual;
import com.example.geovision.models.Paciente;
import com.example.geovision.security.UsuarioPrincipal;
import com.example.geovision.service.ExamenAgudezaVisualService;
import com.example.geovision.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/examenes")
@RequiredArgsConstructor
public class ExamenAgudezaVisualController {

    private final ExamenAgudezaVisualService examenService;
    private final PacienteService pacienteService;
    private final com.example.geovision.service.RecetaOftalmicaInternaService recetaService;

    @GetMapping("/nuevo")
    public String nuevoExamenFormulario(Model model) {
        model.addAttribute("examen", new ExamenAgudezaVisual());
        model.addAttribute("pacientes", pacienteService.findAll()); // For modal selection
        return "examenes/formulario";
    }

    @PostMapping
    public String crearExamen(@ModelAttribute ExamenAgudezaVisual examen,
                              @RequestParam("idPacienteSeleccionado") Long idPaciente,
                              @RequestParam(value = "generarReceta", required = false) Boolean generarReceta,
                              @AuthenticationPrincipal UsuarioPrincipal principal,
                              RedirectAttributes redirectAttributes) {
        Paciente paciente = pacienteService.findById(idPaciente);
        examen.setPaciente(paciente);
        examen.setEmpleadoRegistro(principal.getUsuario().getEmpleado());
        examen.setFechaExamen(LocalDate.now());
        examen.setEstado("vigente"); // Fix for DB constraint

        examenService.save(examen);

        if (Boolean.TRUE.equals(generarReceta)) {
            com.example.geovision.models.RecetaOftalmicaInterna receta = new com.example.geovision.models.RecetaOftalmicaInterna();
            receta.setExamen(examen);
            receta.setPaciente(paciente);
            receta.setEmpleadoEmisor(principal.getUsuario().getEmpleado());
            receta.setFechaEmision(LocalDate.now());
            receta.setEstado("generada");
            String uniqueCode = "REC-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            receta.setCodigoReceta(uniqueCode);
            recetaService.save(receta);
            
            redirectAttributes.addFlashAttribute("mensajeExitoTitulo", "¡Éxito! Examen y receta generados correctamente.");
            redirectAttributes.addFlashAttribute("mensajeExito", "El examen y la receta oftálmica han sido guardados en la base de datos.");
        } else {
            redirectAttributes.addFlashAttribute("mensajeExitoTitulo", "¡Éxito! Examen visual registrado correctamente.");
            redirectAttributes.addFlashAttribute("mensajeExito", "El examen ha sido asociado al paciente y está disponible en su historial.");
        }
        
        return "redirect:/pacientes";
    }
}
