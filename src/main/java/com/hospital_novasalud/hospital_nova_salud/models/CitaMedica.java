package com.hospital_novasalud.hospital_nova_salud.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="citas_medicas")
public class CitaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    private LocalDate fechaCita;
    private LocalTime horaCita;

    public CitaMedica() {
    }
    public CitaMedica(Long id, Paciente paciente, Doctor doctor, LocalDate fechaCita, LocalTime horaCita) {
        this.id = id;
        this.paciente = paciente;
        this.doctor = doctor;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
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
