package com.hospital_novasalud.hospital_nova_salud.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Paciente;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByUsuarioId(Long id);
}
