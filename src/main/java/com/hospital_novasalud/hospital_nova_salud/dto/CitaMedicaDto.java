package com.hospital_novasalud.hospital_nova_salud.dto;


import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;

import jakarta.validation.constraints.NotNull;


public class CitaMedicaDto {
    @NotNull
    private Long doctorId;
    @NotNull
    private Long horaCita;

    public CitaMedicaDto() {
    }

    public CitaMedicaDto(CitaMedica cita) {
        this.doctorId = cita.getDoctor().getId();
        this.horaCita = cita.getHorarioDoctor().getId();
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctor) {
        this.doctorId = doctor;
    }

    public Long getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Long citaMedicaId) {
        this.horaCita = citaMedicaId;
    }

    
   

}
