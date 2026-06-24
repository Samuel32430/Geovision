package com.example.geovision.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class SeguridadController {

    @GetMapping("/seguridad")
    public String inicio(Model model, Authentication authentication) {
        long totalModulosUsuario = authentication.getAuthorities().stream()
                .filter(autoridad -> autoridad.getAuthority().startsWith("MODULO_"))
                .count();

        model.addAttribute("totalModulosUsuario", totalModulosUsuario);
        model.addAttribute("fechaHoy", formatearFechaHoy());
        return "seguridad/inicio";
    }

    private String formatearFechaHoy() {
        String fecha = LocalDate.now().format(
                DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES")));
        return fecha.substring(0, 1).toUpperCase(new Locale("es", "ES")) + fecha.substring(1);
    }
}
