
package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import com.hospital_novasalud.hospital_nova_salud.dto.BoletaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

public interface ICitaMedicaService {
    List<CitaMedicaEnvioDto> findAll();
    List<CitaMedicaEnvioDto> findByPaciente();
    ValidarHorario save(CitaMedicaDto citaMedica);
    void deleteById(Long id);
    BoletaDto generarBoletaParaCita(Long citaId);
    CitaMedicaEnvioDto saveAndReturnDto(CitaMedicaDto cita);
}
