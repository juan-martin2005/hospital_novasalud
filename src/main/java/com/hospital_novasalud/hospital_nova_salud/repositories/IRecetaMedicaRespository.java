package com.hospital_novasalud.hospital_nova_salud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.RecetaMedica;

public interface IRecetaMedicaRespository extends JpaRepository<RecetaMedica, Long>{
    boolean existsRecetaMedicaById(Long id);
}
