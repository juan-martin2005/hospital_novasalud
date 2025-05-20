package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Paciente;

public class PacienteDto {
    private final String dni;
    private final String nombre;
    private final String apellido;
    private final String numero;
    private final char sexo;

    public PacienteDto(Paciente pa){
        this.dni = pa.getDni();
        this.nombre = pa.getUsuario().getNombre();
        this.apellido = pa.getUsuario().getApellido();
        this.numero = pa.getUsuario().getNumero();
        this.sexo = pa.getUsuario().getSexo();
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

    public String getNumero() {
        return numero;
    }

    public char getSexo() {
        return sexo;
    }
    
   

}
