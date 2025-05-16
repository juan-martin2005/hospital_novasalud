package com.hospital_novasalud.hospital_nova_salud.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Paciente;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByUsuarioId(Long id);
    List<Paciente> findByUsuario_EstadoId(int id);
}
