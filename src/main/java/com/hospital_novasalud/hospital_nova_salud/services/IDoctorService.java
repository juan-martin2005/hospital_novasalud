package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.dto.DoctorEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.GestionCitaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.HorarioDoctorDto;
import com.hospital_novasalud.hospital_nova_salud.dto.HorarioDoctorEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

public interface IDoctorService {
    List<DoctorEnvioDto> findAll();
    List<CitaMedicaEnvioDto> findCitasByDoctor();
    List<DoctorDto> findByEspecialidad(Long id);
    Optional<Doctor> findDoctorById(Long id);
    Validaciones save(DoctorDto doc);
    ValidarHorario registrarHorario(HorarioDoctorDto horarioDoctor);
    Validaciones deleteById(Long id);
    boolean existsByEspecialidadId(Long id);
    boolean existsByUsuarioId(Long id);
    Especialidad findEspecialidad(DoctorDto doc);
    List<HorarioDoctorEnvioDto> findHorarioDoctor();
    GestionCitaDto finalizarCita(GestionCitaDto gestionCita, Long id);
    List<HorarioDoctorEnvioDto> findHorarioByDoctorId(Long doctorId); //Método abstracto para getear horario por doctor
}
