package com.hospital_novasalud.hospital_nova_salud.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;

public class CitaMedicaEnvioDto {
    private final String paciente;
    private final String doctor;
    private final LocalDate fechaCita;
    private final LocalTime horaCita;

    
    public CitaMedicaEnvioDto(CitaMedica cita) {
        this.paciente = cita.getPaciente().getUsuario().getNombre();
        this.doctor = "Dr. " + cita.getDoctor().getUsuario().getNombre();
        this.fechaCita = cita.getFechaCita();
        this.horaCita = cita.getHoraCita();
    }

    public String getPaciente() {
        return paciente;
    }

    public String getDoctor() {
        return doctor;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }
}
