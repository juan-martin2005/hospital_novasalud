package com.hospital_novasalud.hospital_nova_salud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Rol;

public interface IRolRepository extends CrudRepository<Rol, Integer> {
    Rol findByNombreRol(String nameRol);
    Rol findById(int id);
    boolean existsById(int id);
}
