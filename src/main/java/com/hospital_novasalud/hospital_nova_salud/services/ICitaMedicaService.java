
package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.ValidacionHorario;

public interface ICitaMedicaService {
    List<CitaMedicaDto> findAll();
    ValidacionHorario save(CitaMedica citaMedica);
    void deleteById(Long id);
}
