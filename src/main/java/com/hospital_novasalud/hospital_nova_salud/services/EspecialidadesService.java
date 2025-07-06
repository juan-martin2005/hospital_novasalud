package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.EspecialidadDto;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEspecialidadRepository;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;


@Service
public class EspecialidadesService implements IEspecialidadesService{

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    @Override
    public List<EspecialidadDto> findAll() {
        return especialidadRepository.findAll().stream().map(EspecialidadDto::new).toList();
    }
    @Override
    public List<EspecialidadDto> findById(Long id) {
        Optional<Especialidad> especialidadOpt = especialidadRepository.findById(id);
        if (especialidadOpt.isEmpty()) {
            return List.of(); // Retorna una lista vacía si no se encuentra la especialidad
        }
        return especialidadOpt.stream().map(EspecialidadDto::new).toList();
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
    public Validaciones update(Long id, Especialidad especialidad) {
        Optional<Especialidad> especialidadOp = especialidadRepository.findById(id);
        if(especialidadOp.isEmpty()){
            return Validaciones.ESPECIALIDAD_NO_ENCONTRADA;
        }
        Especialidad esp = especialidadOp.get();
        esp.setNombre(especialidad.getNombre());
        esp.setDescripcion(especialidad.getDescripcion());
        especialidadRepository.save(esp);
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
