
package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;

public interface ICitaMedicaService {
    List<CitaMedicaDto> findAll();
    boolean save(CitaMedica citaMedica);
    void deleteById(Long id);
}
