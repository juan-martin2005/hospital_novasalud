package com.hospital_novasalud.hospital_nova_salud.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class HorarioDoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable = false)
    private Doctor doctor;
    private LocalDate dia;
    @Column(columnDefinition = "TIME(0)")
    private LocalTime horarioInicio;
    @Column(columnDefinition = "TIME(0)")
    private LocalTime horarioFin;

    public HorarioDoctor() {
    }
    public HorarioDoctor(Doctor doctor, LocalDate dia, LocalTime horarioInicio, LocalTime horarioFin) {
        this.doctor = doctor;
        this.dia = dia;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public LocalDate getDia() {
        return dia;
    }
    public void setDia(LocalDate dia) {
        this.dia = dia;
    }
    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }
    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
    public LocalTime getHorarioFin() {
        return horarioFin;
    }
    public void setHorarioFin(LocalTime horarioFin) {
        this.horarioFin = horarioFin;
    }
    
    
}
