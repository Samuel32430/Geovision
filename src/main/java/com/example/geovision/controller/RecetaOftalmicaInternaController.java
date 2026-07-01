package com.example.geovision.controller;

import com.example.geovision.models.ExamenAgudezaVisual;
import com.example.geovision.models.Paciente;
import com.example.geovision.models.RecetaOftalmicaInterna;
import com.example.geovision.security.UsuarioPrincipal;
import com.example.geovision.service.ExamenAgudezaVisualService;
import com.example.geovision.service.PacienteService;
import com.example.geovision.service.RecetaOftalmicaInternaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/recetas")
@RequiredArgsConstructor
public class RecetaOftalmicaInternaController {

    private final RecetaOftalmicaInternaService recetaService;
    private final ExamenAgudezaVisualService examenService;
    private final PacienteService pacienteService;

    @GetMapping("/nuevo")
    public String nuevaRecetaFormulario(@RequestParam(required = false) Long idPaciente,
                                        @RequestParam(required = false) Long idExamen, Model model) {
        model.addAttribute("pacientes", pacienteService.findAll());
        
        if (idPaciente != null) {
            model.addAttribute("pacienteSeleccionado", pacienteService.findById(idPaciente));
            model.addAttribute("examenesPaciente", examenService.getHistorialByPaciente(idPaciente));
            
            Optional<ExamenAgudezaVisual> optExamen;
            if (idExamen != null) {
                optExamen = Optional.ofNullable(examenService.findById(idExamen));
            } else {
                optExamen = examenService.getLatestExamenByPaciente(idPaciente);
            }

            if (optExamen.isPresent()) {
                model.addAttribute("examen", optExamen.get());
            } else {
                model.addAttribute("error", "El paciente seleccionado no tiene exámenes visuales registrados.");
            }
        }
        
        return "recetas/formulario";
    }

    @PostMapping("/generar")
    public String generarReceta(@RequestParam("idExamen") Long idExamen,
                                @AuthenticationPrincipal UsuarioPrincipal principal,
                                RedirectAttributes redirectAttributes) {
                                    
        if (recetaService.existsByExamenId(idExamen)) {
            redirectAttributes.addFlashAttribute("error", "Ya se ha generado una receta para este examen visual anteriormente.");
            ExamenAgudezaVisual examen = examenService.findById(idExamen);
            return "redirect:/recetas/nuevo?idPaciente=" + examen.getPaciente().getId() + "&idExamen=" + idExamen;
        }

        ExamenAgudezaVisual examen = examenService.findById(idExamen);
        
        RecetaOftalmicaInterna receta = new RecetaOftalmicaInterna();
        receta.setExamen(examen);
        receta.setPaciente(examen.getPaciente());
        receta.setEmpleadoEmisor(principal.getUsuario().getEmpleado());
        receta.setFechaEmision(LocalDate.now());
        receta.setEstado("generada");
        
        // Generate a random unique code for the prescription
        String uniqueCode = "REC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        receta.setCodigoReceta(uniqueCode);
        
        recetaService.save(receta);
        
        redirectAttributes.addFlashAttribute("mensajeExitoTitulo", "¡Éxito! Receta generada correctamente.");
        redirectAttributes.addFlashAttribute("mensajeExito", "La receta oftálmica ha sido guardada en la base de datos.");
        return "redirect:/pacientes";
    }

    @PostMapping("/editar-examen")
    public String editarExamenModal(@ModelAttribute ExamenAgudezaVisual datosEditados,
                                    @AuthenticationPrincipal UsuarioPrincipal principal,
                                    RedirectAttributes redirectAttributes) {
        
        ExamenAgudezaVisual examenExistente = examenService.findById(datosEditados.getId());
        
        // Update values
        examenExistente.setEsferaOd(datosEditados.getEsferaOd());
        examenExistente.setCilindroOd(datosEditados.getCilindroOd());
        examenExistente.setEjeOd(datosEditados.getEjeOd());
        examenExistente.setEsferaOi(datosEditados.getEsferaOi());
        examenExistente.setCilindroOi(datosEditados.getCilindroOi());
        examenExistente.setEjeOi(datosEditados.getEjeOi());
        examenExistente.setDip(datosEditados.getDip());
        examenExistente.setAdicion(datosEditados.getAdicion());
        examenExistente.setMaterialRecomendado(datosEditados.getMaterialRecomendado());
        examenExistente.setObservaciones(datosEditados.getObservaciones());
        
        // Save
        examenService.save(examenExistente);
        
        return "redirect:/recetas/nuevo?idPaciente=" + examenExistente.getPaciente().getId();
    }
}
