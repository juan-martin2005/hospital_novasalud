package com.hospital_novasalud.hospital_nova_salud.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;

public class HorarioDoctorDto {
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public HorarioDoctorDto() {
    }
    public HorarioDoctorDto(HorarioDoctor horarioDoctor) {
        this.fecha = horarioDoctor.getFecha();
        this.horaInicio = horarioDoctor.getHorarioInicio();
        this.horaFin = horarioDoctor.getHorarioFin();
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    public LocalTime getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    

}
