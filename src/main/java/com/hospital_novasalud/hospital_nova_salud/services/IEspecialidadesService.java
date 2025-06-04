
package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import com.hospital_novasalud.hospital_nova_salud.dto.EspecialidadDto;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;

public interface IEspecialidadesService {
    List<EspecialidadDto> findAll();
    boolean save(Especialidad especialidad);
    void deleteById(Long id);
}
