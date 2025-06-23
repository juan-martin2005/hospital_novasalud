
package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import com.hospital_novasalud.hospital_nova_salud.dto.EspecialidadDto;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;

public interface IEspecialidadesService {
    List<EspecialidadDto> findAll();
    Validaciones save(Especialidad especialidad);
    Validaciones deleteById(Long id);
}
