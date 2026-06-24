package com.example.geovision.controller;

import com.example.geovision.models.Empleado;
import com.example.geovision.models.Persona;
import com.example.geovision.models.Rol;
import com.example.geovision.models.Usuario;
import com.example.geovision.service.EmpleadoService;
import com.example.geovision.service.PersonaService;
import com.example.geovision.service.RolService;
import com.example.geovision.service.UsuarioService;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmpleadoService empleadoService;
    private final PersonaService personaService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public String listar(@RequestParam(name = "q", required = false) String q, Model model) {
        List<Usuario> usuarios = StreamSupport.stream(usuarioService.findAll().spliterator(), false).toList();
        if (StringUtils.hasText(q)) {
            String texto = q.toLowerCase();
            usuarios = usuarios.stream()
                    .filter(usuario -> usuario.getUsuarioLogin().toLowerCase().contains(texto)
                            || usuario.getRol().getNombreRol().toLowerCase().contains(texto))
                    .toList();
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("q", q);
        return "usuarios/listar";
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
                         @RequestParam String contrasenaInicial) {
        LocalDate hoy = LocalDate.now();

        Persona persona = datosFormulario.getEmpleado().getPersona();
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
                              @RequestParam(name = "nuevaContrasena", required = false) String nuevaContrasena) {
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
