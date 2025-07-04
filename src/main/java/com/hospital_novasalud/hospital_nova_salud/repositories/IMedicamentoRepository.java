package com.hospital_novasalud.hospital_nova_salud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Medicamento;

public interface IMedicamentoRepository extends JpaRepository<Medicamento, Long>{
    Optional<Medicamento> findByNombre(String nombre);
}
