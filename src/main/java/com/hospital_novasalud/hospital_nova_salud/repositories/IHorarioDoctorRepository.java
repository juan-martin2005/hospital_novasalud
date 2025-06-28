package com.hospital_novasalud.hospital_nova_salud.repositories;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;

public interface IHorarioDoctorRepository extends JpaRepository<HorarioDoctor, Long> {

    boolean existsByHorarioInicio(LocalTime horarioInicio);
    boolean existsByHorarioFin(LocalTime horarioFin);
    boolean existsByFecha(LocalDate dia);
}
