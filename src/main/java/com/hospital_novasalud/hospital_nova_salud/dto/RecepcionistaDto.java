package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;

public class RecepcionistaDto {

    private String nombre;
    private String apellido;
    private String numero;
    private char sexo;

    public RecepcionistaDto(Recepcionista re){
        this.nombre = re.getUsuario().getNombre();
        this.apellido = re.getUsuario().getApellido();
        this.numero = re.getUsuario().getNumero();
        this.sexo = re.getUsuario().getSexo();
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
