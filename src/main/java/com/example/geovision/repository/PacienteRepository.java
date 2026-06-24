package com.example.geovision.repository;

import com.example.geovision.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    @Query("SELECT p FROM Paciente p JOIN p.persona per WHERE " +
           "LOWER(per.nombres) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(per.apellidos) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "per.dni = :busqueda OR " +
           "per.telefono = :busqueda")
    List<Paciente> buscarPorNombreTelefonoODni(@Param("busqueda") String busqueda);
    
    @Query("SELECT p FROM Paciente p JOIN p.persona per WHERE per.dni = :dni")
    Paciente findByDni(@Param("dni") String dni);
}
