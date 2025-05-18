package com.hospital_novasalud.hospital_nova_salud.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{
    boolean existsByNombreUsuaAndContrasena(String n, String c);
    boolean existsByNombreUsua(String n);
    Optional<Usuario> findByNombreUsua(String usuario);
    boolean existsByDni(String dni);
    Usuario findByDni(String dni);
    List<Usuario> findAllByEstadoId(int estado);
}
