package com.hospital_novasalud.hospital_nova_salud.dto;

import java.time.LocalTime;

import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

public class DoctorDto {
    private String nombre;
    private String apellido;
    private String numero;
    private char sexo;
    private String especialidad;
    private LocalTime horarioAtencion;

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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LocalTime getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(LocalTime horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

}
