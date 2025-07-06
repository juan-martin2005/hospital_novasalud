package com.hospital_novasalud.hospital_nova_salud.dto;

import java.time.LocalDateTime;

import com.hospital_novasalud.hospital_nova_salud.models.Boleta;

public class BoletaDto {
    private Long id;
    private String paciente;
    private String doctor;
    private LocalDateTime fecha;
    private Double monto;
     public BoletaDto(Boleta boleta) {
        this(
            boleta.getId(),
            boleta.getPaciente().getUsuario().getNombre() + " " + boleta.getPaciente().getUsuario().getApellido(),
            "Dr. " + boleta.getDoctor().getUsuario().getNombre() + " " + boleta.getDoctor().getUsuario().getApellido(),
            boleta.getFecha(),
            boleta.getMonto()
        );
    }
    public BoletaDto(Long id, String paciente, String doctor, LocalDateTime fecha, Double monto) {
        this.id = id;
        this.paciente = paciente;
        this.doctor = doctor;
        this.fecha = fecha;
        this.monto = monto;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPaciente() {
        return paciente;
    }
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }
    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public Double getMonto() {
        return monto;
    }
    public void setMonto(Double monto) {
        this.monto = monto;
    }
    
}
