package com.example.geovision.controller;

import com.example.geovision.models.Empleado;
import com.example.geovision.models.Persona;

import com.example.geovision.models.Usuario;
import com.example.geovision.service.EmpleadoService;
import com.example.geovision.service.PersonaService;
import com.example.geovision.service.RolService;
import com.example.geovision.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmpleadoService empleadoService;
    private final PersonaService personaService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;

    private static final int USUARIOS_POR_PAGINA = 8;

    @GetMapping("/usuarios")
    public String listar(@RequestParam(name = "q", required = false) String q, @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(max(0, page), USUARIOS_POR_PAGINA, Sort.by("id").ascending());
        Page<Usuario> usuarios = usuarioService.findPage(q, pageable);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("q", q);
        model.addAttribute("paginas", construirPaginas(usuarios.getTotalPages(), usuarios.getNumber()));
        return "usuarios/listar";
    }

    private List<Integer> construirPaginas(int totalPages, int actual) {
        List<Integer> paginas = new ArrayList<>();
        if (totalPages <= 1) {
            paginas.add(0);
            return paginas;
        }

        int desde = max(1, actual - 1);
        int hasta = min(totalPages - 2, actual + 1);

        paginas.add(0);
        if (desde > 1) {
            paginas.add(null);
        }
        for (int i = desde; i <= hasta; i++) {
            paginas.add(i);
        }
        if (hasta < totalPages - 2) {
            paginas.add(null);
        }
        paginas.add(totalPages - 1);
        return paginas;
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevoFormulario(Model model) {
        Usuario usuario = new Usuario();
        Empleado empleado = new Empleado();
        empleado.setPersona(new Persona());
        usuario.setEmpleado(empleado);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.findAll());
        return "usuarios/formulario";
    }

    @GetMapping("/usuarios/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.findById(id));
        model.addAttribute("roles", rolService.findAll());
        return "usuarios/formulario";
    }

    @PostMapping("/usuarios")
    public String crear(@ModelAttribute Usuario datosFormulario, @RequestParam Long idRol,
                         @RequestParam String contrasenaInicial, Model model) {
        Persona persona = datosFormulario.getEmpleado().getPersona();

        List<String> camposConError = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        if (usuarioService.existsByUsuarioLogin(datosFormulario.getUsuarioLogin())) {
            camposConError.add("usuarioLogin");
            mensajes.add("nombre de usuario");
        }
        if (StringUtils.hasText(persona.getDni()) && personaService.existsByDni(persona.getDni())) {
            camposConError.add("dni");
            mensajes.add("documento de identidad (DNI)");
        }

        if (!camposConError.isEmpty()) {
            datosFormulario.setRol(rolService.findById(idRol));
            model.addAttribute("usuario", datosFormulario);
            model.addAttribute("roles", rolService.findAll());
            model.addAttribute("errorRegistro", "Ya existe una cuenta registrada con ese " + String.join(" y ese ", mensajes) + ".");
            model.addAttribute("camposConError", camposConError);
            return "usuarios/formulario";
        }

        LocalDate hoy = LocalDate.now();
        persona.setTipoPersona("empleado");
        persona.setFechaRegistro(hoy);
        persona = personaService.save(persona);

        Empleado empleado = datosFormulario.getEmpleado();
        empleado.setPersona(persona);
        empleado.setEstado("activo");
        empleado = empleadoService.save(empleado);

        Usuario usuario = new Usuario();
        usuario.setEmpleado(empleado);
        usuario.setRol(rolService.findById(idRol));
        usuario.setUsuarioLogin(datosFormulario.getUsuarioLogin());
        usuario.setContrasenaHash(passwordEncoder.encode(contrasenaInicial));
        usuario.setEstado(datosFormulario.getEstado());
        usuario.setFechaRegistro(hoy);
        usuarioService.save(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Usuario datosFormulario,
                              @RequestParam Long idRol,
                              @RequestParam(name = "nuevaContrasena", required = false) String nuevaContrasena,
                              Model model) {
        if (usuarioService.existsByUsuarioLoginAndIdNot(datosFormulario.getUsuarioLogin(), id)) {
            datosFormulario.setId(id);
            datosFormulario.setRol(rolService.findById(idRol));
            model.addAttribute("usuario", datosFormulario);
            model.addAttribute("roles", rolService.findAll());
            model.addAttribute("errorRegistro", "Ya existe una cuenta registrada con ese nombre de usuario.");
            model.addAttribute("camposConError", List.of("usuarioLogin"));
            return "usuarios/formulario";
        }

        Usuario usuario = usuarioService.findById(id);

        Persona persona = usuario.getEmpleado().getPersona();
        Persona datosPersona = datosFormulario.getEmpleado().getPersona();
        persona.setNombres(datosPersona.getNombres());
        persona.setApellidos(datosPersona.getApellidos());
        persona.setTelefono(datosPersona.getTelefono());
        persona.setCorreo(datosPersona.getCorreo());
        personaService.save(persona);

        Empleado empleado = usuario.getEmpleado();
        Empleado datosEmpleado = datosFormulario.getEmpleado();
        empleado.setCargo(datosEmpleado.getCargo());
        empleado.setFechaIngreso(datosEmpleado.getFechaIngreso());
        empleadoService.save(empleado);

        usuario.setRol(rolService.findById(idRol));
        usuario.setUsuarioLogin(datosFormulario.getUsuarioLogin());
        usuario.setEstado(datosFormulario.getEstado());
        if (StringUtils.hasText(nuevaContrasena)) {
            usuario.setContrasenaHash(passwordEncoder.encode(nuevaContrasena));
        }
        usuarioService.save(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/{id}/estado")
    public String cambiarEstado(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        usuario.setEstado("activo".equals(usuario.getEstado()) ? "inactivo" : "activo");
        usuarioService.save(usuario);
        return "redirect:/usuarios";
    }
    
    
}
