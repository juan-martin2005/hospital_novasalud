package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Paciente;

public class PacienteEnvioDto {
    private final Long id;
    private final String dni;
    private final String nombre;
    private final String apellido;
    public PacienteEnvioDto(Paciente paciente) {
        this.id = paciente.getId();
        this.dni = paciente.getDni();
        this.nombre = paciente.getUsuario().getNombre();
        this.apellido = paciente.getUsuario().getApellido();
    }
    public Long getId() {
        return id;
    }
    public String getDni() {
        return dni;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    
}
