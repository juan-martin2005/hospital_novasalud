package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;

public interface IPacienteService {
    List<PacienteDto> findAll();
    boolean existsByUsuarioId(Long id);
    ResponseEntity<?> save(Paciente paciente);
    void delete(Long id);
}
