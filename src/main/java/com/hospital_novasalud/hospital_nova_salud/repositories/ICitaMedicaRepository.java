package com.hospital_novasalud.hospital_nova_salud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.models.EstadoCitaEnum;

public interface ICitaMedicaRepository extends JpaRepository<CitaMedica, Long>{

    List<CitaMedica>findByPaciente_Id(Long id);
    boolean existsByHorarioDoctor_Id(Long id);
    List<CitaMedica> findByHorarioDoctorId(Long horarioDoctorId);
    List<CitaMedica> findByEstado(EstadoCitaEnum estado);
    List<CitaMedica> findByDoctor_IdAndEstado(Long id, EstadoCitaEnum estado);
    List<CitaMedica> findByDoctor_Id(Long doctorId); //Decidi ser feliz y crear un método en la interfaz para getear doctor por id
}
