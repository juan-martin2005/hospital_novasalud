package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Paciente;

public class PacienteDto {
    private String dni;
    private String nombre;
    private String apellido;
    private String numero;
    private char sexo;

    public PacienteDto(Paciente pa){
        this.dni = pa.getUsuario().getDni();
        this.nombre = pa.getUsuario().getNombre();
        this.apellido = pa.getUsuario().getApellido();
        this.numero = pa.getUsuario().getNumero();
        this.sexo = pa.getUsuario().getSexo();
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
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

}
