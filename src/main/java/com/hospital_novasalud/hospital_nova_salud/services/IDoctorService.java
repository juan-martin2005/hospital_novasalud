package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.ValidacionHorario;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.Validaciones;

public interface IDoctorService {

    List<DoctorDto> findAll();
    List<DoctorDto> findByEspecialidad(Long id);
    Optional<Doctor> findDoctorById(Long id);
    Validaciones save(Doctor doc);
    ValidacionHorario updateHorario(HorarioDoctor horarioDoctor);
    Validaciones deleteById(Long id);
    boolean existsByEspecialidadId(Long id);
    boolean existsByUsuarioId(Long id);
    Especialidad findEspecialidad(Doctor doc);

}
