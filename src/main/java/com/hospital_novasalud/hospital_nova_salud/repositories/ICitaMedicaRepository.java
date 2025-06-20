package com.hospital_novasalud.hospital_nova_salud.repositories;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

public interface ICitaMedicaRepository extends JpaRepository<CitaMedica, Long>{

    boolean existsByHoraCita(LocalTime horaCita);

    boolean existsByDoctorAndFechaCitaAndHoraCita(Doctor orElseThrow, LocalDate fechaCita, LocalTime horaCita);

}
