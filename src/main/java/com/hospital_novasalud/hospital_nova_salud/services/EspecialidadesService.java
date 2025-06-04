package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.EspecialidadDto;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEspecialidadRepository;

@Service
public class EspecialidadesService implements IEspecialidadesService{

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    @Override
    public List<EspecialidadDto> findAll() {
        return especialidadRepository.findAll().stream().map(EspecialidadDto::new).toList();
    }

    @Override
    public boolean save(Especialidad especialidad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
