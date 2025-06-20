package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.EspecialidadDto;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEspecialidadRepository;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.Validaciones;

@Service
public class EspecialidadesService implements IEspecialidadesService{

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    @Override
    public List<EspecialidadDto> findAll() {
        return especialidadRepository.findAll().stream().map(EspecialidadDto::new).toList();
    }

    @Override
    public Validaciones save(Especialidad especialidad) {
        boolean existeEspecialidad = especialidadRepository.existsByNombre(especialidad.getNombre());
        if(existeEspecialidad){
            return Validaciones.YA_EXISTE;
        }
        especialidadRepository.save(especialidad);
        return Validaciones.OK;
    }

    @Override
    public Validaciones deleteById(Long id) {
        boolean existe = especialidadRepository.existsById(id);
        if(!existe){
            return Validaciones.ESPECIALIDAD_NO_ENCONTRADA;
        }
        especialidadRepository.deleteById(id);
        return Validaciones.OK;

    }

}
