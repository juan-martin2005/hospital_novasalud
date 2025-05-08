package com.hospital_novasalud.hospital_nova_salud.services;

import org.springframework.http.ResponseEntity;

import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

public interface IUsuarioService {

    boolean existsByNombreUsuarioAndContrasena(String n, String c);
    boolean existsByNombreUsuario(String n);
    Iterable<Usuario> findAll();
    ResponseEntity<?> save(Usuario us);
    void deleteById(Long id);

}
