package com.example.geovision.controller;

import com.example.geovision.models.Permiso;
import com.example.geovision.models.Rol;
import com.example.geovision.models.RolPermiso;
import com.example.geovision.service.PermisoService;
import com.example.geovision.service.RolPermisoService;
import com.example.geovision.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;
    private final PermisoService permisoService;
    private final RolPermisoService rolPermisoService;

    @GetMapping("/roles")
    public String listar(@RequestParam(name = "q", required = false) String q, Model model) {
        List<Rol> roles = StreamSupport.stream(rolService.findAll().spliterator(), false).toList();
        if (StringUtils.hasText(q)) {
            String texto = q.toLowerCase();
            roles = roles.stream()
                    .filter(rol -> rol.getNombreRol().toLowerCase().contains(texto))
                    .toList();
        }
        model.addAttribute("roles", roles);
        model.addAttribute("q", q);
        model.addAttribute("permisosPorRol", roles.stream()
                .collect(Collectors.toMap(Rol::getId, rol -> rolPermisoService.findByRolId(rol.getId()).stream()
                        .map(rolPermiso -> rolPermiso.getPermiso().getModulo().getNombreModulo())
                        .toList())));
        return "roles/listar";
    }

    @GetMapping("/roles/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("rol", new Rol());
        model.addAttribute("permisos", permisoService.findAll());
        model.addAttribute("idsPermisoAsignados", Set.of());
        return "roles/formulario";
    }

    @GetMapping("/roles/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("rol", rolService.findById(id));
        model.addAttribute("permisos", permisoService.findAll());
        model.addAttribute("idsPermisoAsignados", rolPermisoService.findByRolId(id).stream()
                .map(rolPermiso -> rolPermiso.getPermiso().getId())
                .collect(Collectors.toSet()));
        return "roles/formulario";
    }

    @PostMapping("/roles")
    public String crear(@ModelAttribute Rol datosFormulario,
                         @RequestParam(name = "idsPermiso", required = false) List<Long> idsPermiso) {
        datosFormulario.setEstado(StringUtils.hasText(datosFormulario.getEstado()) ? datosFormulario.getEstado() : "activo");
        Rol rol = rolService.save(datosFormulario);
        asignarPermisos(rol, idsPermiso);
        return "redirect:/roles";
    }

    @PostMapping("/roles/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Rol datosFormulario,
                              @RequestParam(name = "idsPermiso", required = false) List<Long> idsPermiso) {
        Rol rol = rolService.findById(id);
        rol.setNombreRol(datosFormulario.getNombreRol());
        rol.setDescripcion(datosFormulario.getDescripcion());
        rol.setEstado(datosFormulario.getEstado());
        rolService.save(rol);

        rolPermisoService.findByRolId(id).forEach(rolPermiso -> rolPermisoService.delete(rolPermiso.getId()));
        asignarPermisos(rol, idsPermiso);
        return "redirect:/roles";
    }

    @PostMapping("/roles/{id}/estado")
    public String cambiarEstado(@PathVariable Long id) {
        Rol rol = rolService.findById(id);
        rol.setEstado("activo".equals(rol.getEstado()) ? "inactivo" : "activo");
        rolService.save(rol);
        return "redirect:/roles";
    }

    private void asignarPermisos(Rol rol, List<Long> idsPermiso) {
        if (idsPermiso == null) {
            return;
        }
        for (Long idPermiso : idsPermiso) {
            Permiso permiso = permisoService.findById(idPermiso);
            RolPermiso rolPermiso = new RolPermiso();
            rolPermiso.setRol(rol);
            rolPermiso.setPermiso(permiso);
            rolPermisoService.save(rolPermiso);
        }
    }
}
