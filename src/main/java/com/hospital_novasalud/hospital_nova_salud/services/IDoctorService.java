package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.dto.DoctorEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.HorarioDoctorDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

public interface IDoctorService {

    List<DoctorEnvioDto> findAll();
    List<DoctorDto> findByEspecialidad(Long id);
    Optional<Doctor> findDoctorById(Long id);
    Validaciones save(DoctorDto doc);
    ValidarHorario registrarHorario(HorarioDoctorDto horarioDoctor);
    Validaciones deleteById(Long id);
    boolean existsByEspecialidadId(Long id);
    boolean existsByUsuarioId(Long id);
    Especialidad findEspecialidad(DoctorDto doc);

}
