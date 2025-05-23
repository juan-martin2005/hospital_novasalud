package com.hospital_novasalud.hospital_nova_salud.dto;

import java.time.LocalTime;

import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

public class DoctorDto {
    private final String nombre;
    private final String apellido;
    private final String numero;
    private final char sexo;
    private final String especialidad;
    private final LocalTime horarioAtencion;

    public DoctorDto(Doctor doc){
        this.nombre = doc.getUsuario().getNombre();
        this.apellido = doc.getUsuario().getApellido();
        this.numero = doc.getUsuario().getNumero();
        this.sexo = doc.getUsuario().getSexo();
        this.especialidad = doc.getEspecialidad().getNombre();
        this.horarioAtencion = doc.getHorarioAtencion();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNumero() {
        return numero;
    }

    public char getSexo() {
        return sexo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public LocalTime getHorarioAtencion() {
        return horarioAtencion;
    }

    
}
