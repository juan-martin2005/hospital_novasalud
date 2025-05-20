package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;

public class RecepcionistaDto {

    private final String nombre;
    private final String apellido;
    private final String numero;
    private final char sexo;

    public RecepcionistaDto(Recepcionista re){
        this.nombre = re.getUsuario().getNombre();
        this.apellido = re.getUsuario().getApellido();
        this.numero = re.getUsuario().getNumero();
        this.sexo = re.getUsuario().getSexo();
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
