package com.hospital_novasalud.hospital_nova_salud.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

import java.util.List;


public interface IDoctorRepository extends JpaRepository<Doctor, Long>{
    boolean existsByEspecialidadId(Long id);
    boolean existsByUsuarioId(Long id);
    List<Doctor> findByEspecialidadId(Long id);
    List<Doctor> findByUsuario_EstadoId(int id);
    Doctor findByUsuarioId(Long id);

}
