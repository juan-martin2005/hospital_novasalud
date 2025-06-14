package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

public class DoctorDto {
    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String especialidad;


    public DoctorDto(Doctor doc){
        this.id = doc.getId();
        this.nombre = doc.getUsuario().getNombre();
        this.apellido = doc.getUsuario().getApellido();
        this.especialidad = doc.getEspecialidad().getNombre();
    }

    public Long getId() {
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
