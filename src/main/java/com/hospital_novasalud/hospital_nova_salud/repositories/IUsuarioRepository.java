package com.hospital_novasalud.hospital_nova_salud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{
    boolean existsByNombreUsuaAndContrasena(String n, String c);
    boolean existsByNombreUsua(String n);
}
