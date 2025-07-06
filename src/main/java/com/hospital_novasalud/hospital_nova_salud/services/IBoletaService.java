package com.hospital_novasalud.hospital_nova_salud.services;
import java.util.List;

import com.hospital_novasalud.hospital_nova_salud.dto.BoletaDto;

public interface IBoletaService {
    List<BoletaDto> findBoletasByPaciente();
}
