package com.hospital_novasalud.hospital_nova_salud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Estado;

public interface IEstadoRepository extends JpaRepository<Estado, Integer> {
}
