package com.hospital_novasalud.hospital_nova_salud.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="recetas_medicas")
public class RecetaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable = false)
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name="paciente_id", nullable = false)
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name="medicamento_id", nullable = false)
    private Medicamento medicamento;
    private String dosis;
    private String frecuencia;
    private String fechaCreada;
    public RecetaMedica() {
    }
    public RecetaMedica(Long id, Doctor doctor, Paciente paciente, Medicamento medicamento, String dosis,
            String frecuencia, String fechaCreada) {
        this.id = id;
        this.doctor = doctor;
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.fechaCreada = fechaCreada;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public Medicamento getMedicamento() {
        return medicamento;
    }
    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
    public String getDosis() {
        return dosis;
    }
    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
    public String getFrecuencia() {
        return frecuencia;
    }
    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
    public String getFechaCreada() {
        return fechaCreada;
    }
    public void setFechaCreada(String fechaCreada) {
        this.fechaCreada = fechaCreada;
    }


    

}
