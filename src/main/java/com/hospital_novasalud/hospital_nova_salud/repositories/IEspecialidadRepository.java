package com.hospital_novasalud.hospital_nova_salud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;

public interface IEspecialidadRepository extends JpaRepository<Especialidad, Long>{
    boolean existsByNombre(String nombre);
}
