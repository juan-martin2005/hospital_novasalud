package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.dto.PacienteEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;

public interface IPacienteService {
    List<PacienteEnvioDto> findAll();
    Validaciones save(PacienteDto paciente);
    Validaciones deleteById(Long id);
    Optional<Paciente> findPacienteById(Long id);
}
