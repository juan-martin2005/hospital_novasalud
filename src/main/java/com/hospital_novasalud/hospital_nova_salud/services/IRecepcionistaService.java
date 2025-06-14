package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import com.hospital_novasalud.hospital_nova_salud.dto.RecepcionistaDto;
import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.Validaciones;

public interface IRecepcionistaService {
    List<RecepcionistaDto> findAll();
    RecepcionistaDto findById(Long id);
    Validaciones save (Recepcionista re);
    Validaciones deleteById(Long id);
    boolean existsByUsuarioId(Long id);
    Optional<Recepcionista> findRecepcionistaById(Long id);
}
