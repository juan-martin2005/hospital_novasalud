package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

public interface IDoctorService {

    List<DoctorDto> findAll();
    ResponseEntity<?> save(Doctor doc);
    void deleteById(Long id);
    boolean existsByEspecialidadId(Long id);
    boolean existsByUsuarioId(Long id);
}
