package com.hospital_novasalud.hospital_nova_salud.models;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @ManyToOne
    @JoinColumn(name = "horario_id", nullable = false)
    HorarioDoctor horarioDoctor;
    
    @Enumerated(EnumType.STRING)
    private EstadoCitaEnum estado;

    public CitaMedica() {
    }
    public CitaMedica(Paciente paciente, Doctor doctor, HorarioDoctor horarioDoctor) {
        this.paciente = paciente;
        this.doctor = doctor;
        this.horarioDoctor = horarioDoctor;
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
    public HorarioDoctor getHorarioDoctor() {
        return horarioDoctor;
    }
    public void setHorarioDoctor(HorarioDoctor horarioDoctor) {
        this.horarioDoctor = horarioDoctor;
    }
    
    public EstadoCitaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCitaEnum estado) {
        this.estado = estado;
    }
}
