package com.hospital_novasalud.hospital_nova_salud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;

public interface ICitaMedicaRepository extends JpaRepository<CitaMedica, Long>{

    List<CitaMedica>findByPaciente_Id(Long id);
}
