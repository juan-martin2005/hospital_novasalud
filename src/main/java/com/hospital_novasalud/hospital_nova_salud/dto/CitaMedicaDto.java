package com.hospital_novasalud.hospital_nova_salud.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;

import jakarta.validation.constraints.NotNull;


public class CitaMedicaDto {
    @NotNull
    private Long doctor;
    @NotNull
    private LocalDate fechaCita;
    @NotNull
    private LocalTime horaCita;

    public CitaMedicaDto() {
    }

    public CitaMedicaDto(CitaMedica cita) {
        this.doctor = cita.getDoctor().getId();
        this.fechaCita = cita.getFechaCita();
        this.horaCita = cita.getHoraCita();
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(Long doctor) {
        this.doctor = doctor;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

   

}
