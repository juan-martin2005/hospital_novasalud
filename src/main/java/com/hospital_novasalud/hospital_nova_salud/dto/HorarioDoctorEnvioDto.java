package com.hospital_novasalud.hospital_nova_salud.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;

public class HorarioDoctorEnvioDto {

    private Long id;
    private String nombre;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public HorarioDoctorEnvioDto(HorarioDoctor horarioDoctor) {
        this.id = horarioDoctor.getId();
        this.nombre = horarioDoctor.getDoctor().getUsuario().getNombre();
        this.fecha = horarioDoctor.getFecha();
        this.horaInicio = horarioDoctor.getHorarioInicio();
        this.horaFin = horarioDoctor.getHorarioFin();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
