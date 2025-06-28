package com.hospital_novasalud.hospital_nova_salud.dto;


import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;

import jakarta.validation.constraints.NotNull;


public class CitaMedicaDto {
    @NotNull
    private Long doctorId;
    @NotNull
    private Long citaMedicaId;

    public CitaMedicaDto() {
    }

    public CitaMedicaDto(CitaMedica cita) {
        this.doctorId = cita.getDoctor().getId();
        this.citaMedicaId = cita.getId();
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctor) {
        this.doctorId = doctor;
    }

    public Long getCitaMedicaId() {
        return citaMedicaId;
    }

    public void setCitaMedicaId(Long citaMedicaId) {
        this.citaMedicaId = citaMedicaId;
    }

    
   

}
