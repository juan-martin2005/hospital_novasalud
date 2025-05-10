package com.hospital_novasalud.hospital_nova_salud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

public interface IDoctorRepository extends JpaRepository<Doctor, Long>{
    boolean existsByEspecialidadId(Long id);
    boolean existsByUsuarioId(Long id);
}
