package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

public class UsuarioDto {

    private final String nombreUsua;
    private final String nombre;
    private final String apellido;
    private final String numero;
    private final char sexo;
    private final String rol;

    public UsuarioDto(Usuario usuario) {
        this.nombreUsua = usuario.getNombreUsua();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.numero = usuario.getNumero();
        this.sexo = usuario.getSexo();
        this.rol = usuario.getRol().getNombreRol();
    }

    public String getNombreUsua() {
        return nombreUsua;
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

    public String getRol() {
        return rol;
    }

}
