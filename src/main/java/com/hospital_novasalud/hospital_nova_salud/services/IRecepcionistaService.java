package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hospital_novasalud.hospital_nova_salud.dto.RecepcionistaDto;
import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;

public interface IRecepcionistaService {
    List<RecepcionistaDto> findAll();
    RecepcionistaDto findById(Long id);
    ResponseEntity<?> save (Recepcionista re);
    void deleteById(Long id);
    boolean existsByUsuarioId(Long id);
}
