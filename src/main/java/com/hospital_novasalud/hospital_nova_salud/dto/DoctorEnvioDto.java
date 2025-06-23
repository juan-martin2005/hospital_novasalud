package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

public class DoctorEnvioDto {
    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String especialidad;

    public DoctorEnvioDto(Doctor doctor) {
        this.id = doctor.getId();
        this.nombre = doctor.getUsuario().getNombre();
        this.apellido = doctor.getUsuario().getApellido();
        this.especialidad = doctor.getEspecialidad().getNombre();
    }

    public Long getId(){
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    
}
