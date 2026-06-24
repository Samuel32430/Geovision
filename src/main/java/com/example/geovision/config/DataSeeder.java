package com.example.geovision.config;

import com.example.geovision.models.Empleado;
import com.example.geovision.models.Modulo;
import com.example.geovision.models.Permiso;
import com.example.geovision.models.Persona;
import com.example.geovision.models.Rol;
import com.example.geovision.models.RolPermiso;
import com.example.geovision.models.Usuario;
import com.example.geovision.repository.EmpleadoRepository;
import com.example.geovision.repository.ModuloRepository;
import com.example.geovision.repository.PermisoRepository;
import com.example.geovision.repository.PersonaRepository;
import com.example.geovision.repository.RolPermisoRepository;
import com.example.geovision.repository.RolRepository;
import com.example.geovision.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Semilla F1 del modulo de seguridad: modulos, roles, permisos y los 6
 * usuarios del prototipo "Gestion de Usuarios". Datos de Persona/Empleado
 * (DNI, nombres, telefono) son datos de prueba inventados; login, correo,
 * rol y fecha de registro provienen del mockup.    
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private static final String CONTRASENA_PRUEBA = "123456";

    private final ModuloRepository moduloRepository;
    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;
    private final RolPermisoRepository rolPermisoRepository;
    private final PersonaRepository personaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }

        Modulo atencionPaciente = guardarModulo("Atención al Paciente", "Registro y atención clínica de pacientes.");
        Modulo ventas = guardarModulo("Ventas", "Registro de ventas, pagos e historial.");
        Modulo laboratorio = guardarModulo("Laboratorio", "Órdenes de trabajo y control de calidad.");
        Modulo inventario = guardarModulo("Inventario", "Compras y abastecimiento de stock.");
        Modulo seguridad = guardarModulo("Seguridad", "Usuarios, roles y permisos del sistema.");
        Modulo configuracion = guardarModulo("Configuración", "Parámetros generales del sistema.");

        Permiso permisoAtencionPaciente = guardarPermiso(atencionPaciente);
        Permiso permisoVentas = guardarPermiso(ventas);
        Permiso permisoLaboratorio = guardarPermiso(laboratorio);
        Permiso permisoInventario = guardarPermiso(inventario);
        Permiso permisoSeguridad = guardarPermiso(seguridad);
        Permiso permisoConfiguracion = guardarPermiso(configuracion);

        Rol administrador = guardarRol("Administrador", "Acceso total al sistema.");
        Rol vendedor = guardarRol("Vendedor", "Atención de clientes y registro de ventas.");
        Rol optometra = guardarRol("Optómetra", "Atención clínica y generación de órdenes de laboratorio.");
        Rol tecnicoLaboratorio = guardarRol("Técnico de Laboratorio", "Órdenes de trabajo y control de calidad.");
        Rol encargadoInventario = guardarRol("Encargado de Inventario", "Compras y abastecimiento de stock.");

        asignarPermisos(administrador, permisoAtencionPaciente, permisoVentas, permisoLaboratorio,
                permisoInventario, permisoSeguridad, permisoConfiguracion);
        asignarPermisos(vendedor, permisoAtencionPaciente, permisoVentas);
        asignarPermisos(optometra, permisoAtencionPaciente, permisoLaboratorio);
        asignarPermisos(tecnicoLaboratorio, permisoLaboratorio);
        asignarPermisos(encargadoInventario, permisoInventario);

        crearUsuario("Lina", "Espinoza Vargas", "71234561", "987651001", "lina.espinoza@geovision.pe",
                "Administrador", administrador, "lina.espinoza", "activo", LocalDate.of(2025, 3, 12));
        crearUsuario("Carlos", "Ruiz Mendoza", "71234562", "987651002", "carlos.ruiz@geovision.pe",
                "Vendedor", vendedor, "carlos.ruiz", "activo", LocalDate.of(2025, 6, 4));
        crearUsuario("Gabriela", "Aucapuri Quenaya", "71234563", "987651003", "g.aucapuri@geovision.pe",
                "Optometra", optometra, "g.aucapuri", "activo", LocalDate.of(2025, 1, 19));
        crearUsuario("Diego", "Daniel Soto", "71234564", "987651004", "diego.daniel@geovision.pe",
                "Tecnico de Laboratorio", tecnicoLaboratorio, "diego.daniel", "activo", LocalDate.of(2025, 8, 27));
        crearUsuario("Marta", "Flores Cárdenas", "71234565", "987651005", "marta.flores@geovision.pe",
                "Encargado de Stock", encargadoInventario, "marta.flores", "inactivo", LocalDate.of(2025, 2, 2));
        crearUsuario("Ana", "Quispe Huamán", "71234566", "987651006", "ana.quispe@geovision.pe",
                "Vendedor", vendedor, "ana.quispe", "activo", LocalDate.of(2025, 9, 15));
    }

    private Modulo guardarModulo(String nombre, String descripcion) {
        Modulo modulo = new Modulo();
        modulo.setNombreModulo(nombre);
        modulo.setDescripcion(descripcion);
        modulo.setEstado("activo");
        return moduloRepository.save(modulo);
    }

    private Permiso guardarPermiso(Modulo modulo) {
        Permiso permiso = new Permiso();
        permiso.setModulo(modulo);
        permiso.setNombrePermiso("Acceso a " + modulo.getNombreModulo());
        permiso.setDescripcion("Permite acceder al módulo de " + modulo.getNombreModulo() + ".");
        return permisoRepository.save(permiso);
    }

    private Rol guardarRol(String nombre, String descripcion) {
        Rol rol = new Rol();
        rol.setNombreRol(nombre);
        rol.setDescripcion(descripcion);
        rol.setEstado("activo");
        return rolRepository.save(rol);
    }

    private void asignarPermisos(Rol rol, Permiso... permisos) {
        for (Permiso permiso : permisos) {
            RolPermiso rolPermiso = new RolPermiso();
            rolPermiso.setRol(rol);
            rolPermiso.setPermiso(permiso);
            rolPermisoRepository.save(rolPermiso);
        }
    }

    private void crearUsuario(String nombres, String apellidos, String dni, String telefono, String correo,
                               String cargo, Rol rol, String login, String estadoUsuario, LocalDate fecha) {
        Persona persona = new Persona();
        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setDni(dni);
        persona.setTelefono(telefono);
        persona.setCorreo(correo);
        persona.setTipoPersona("empleado");
        persona.setFechaRegistro(fecha);
        persona = personaRepository.save(persona);

        Empleado empleado = new Empleado();
        empleado.setPersona(persona);
        empleado.setCargo(cargo);
        empleado.setFechaIngreso(fecha);
        empleado.setEstado("activo");
        empleado = empleadoRepository.save(empleado);

        Usuario usuario = new Usuario();
        usuario.setEmpleado(empleado);
        usuario.setRol(rol);
        usuario.setUsuarioLogin(login);
        usuario.setContrasenaHash(passwordEncoder.encode(CONTRASENA_PRUEBA));
        usuario.setEstado(estadoUsuario);
        usuario.setFechaRegistro(fecha);
        usuarioRepository.save(usuario);
    }
}
