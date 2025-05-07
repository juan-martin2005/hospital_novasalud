package com.hospital_novasalud.hospital_nova_salud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Estado;

public interface IEstadoRepository extends CrudRepository<Estado, Integer> {
    Estado findByNombreEstado(String n);
    Estado findById(int n);

}
