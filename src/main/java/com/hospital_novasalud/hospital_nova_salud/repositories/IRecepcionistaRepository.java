package com.hospital_novasalud.hospital_nova_salud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;

public interface IRecepcionistaRepository extends JpaRepository<Recepcionista, Long>{
    boolean existsByUsuarioId(Long id);
    List<Recepcionista> findByUsuario_EstadoId(int id);

}
