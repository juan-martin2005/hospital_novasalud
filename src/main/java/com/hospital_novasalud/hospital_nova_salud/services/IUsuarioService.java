package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import com.hospital_novasalud.hospital_nova_salud.dto.UsuarioDto;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

public interface IUsuarioService {

    List<UsuarioDto> findAll();
    void deleteById(Long id);
    Optional<Usuario> findUsuarioById(Long id);
}
