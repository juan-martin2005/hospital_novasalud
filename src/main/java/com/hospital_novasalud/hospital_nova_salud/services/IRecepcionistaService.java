package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import com.hospital_novasalud.hospital_nova_salud.dto.RecepcionistaDto;
import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;

public interface IRecepcionistaService {
    List<RecepcionistaDto> findAll();
    RecepcionistaDto findById(Long id);
    boolean save (Recepcionista re);
    void deleteById(Long id);
    boolean existsByUsuarioId(Long id);
    Optional<Recepcionista> findRecepcionistaById(Long id);
}
