package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import com.hospital_novasalud.hospital_nova_salud.dto.HorarioDoctorEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.dto.PacienteEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;

public interface IPacienteService {
    List<PacienteEnvioDto> findAll();
    List<HorarioDoctorEnvioDto> findHorarioDoctor(Long id);
    Validaciones save(PacienteDto paciente);
    Validaciones deleteById(Long id);
}
