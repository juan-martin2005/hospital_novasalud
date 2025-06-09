package com.hospital_novasalud.hospital_nova_salud.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

import java.time.LocalTime;
import java.util.List;


public interface IDoctorRepository extends JpaRepository<Doctor, Long>{
    boolean existsByEspecialidadId(Long id);
    boolean existsByUsuarioId(Long id);
    boolean existsByHorarioAtencion(LocalTime horaAtencion);
    List<Doctor> findByEspecialidadId(Long id);
    List<Doctor> findByUsuario_EstadoId(int id);

}
