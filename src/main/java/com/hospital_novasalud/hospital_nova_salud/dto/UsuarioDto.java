package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

public class UsuarioDto {

    private final String dni;
    private final String nombreUsua;
    private final String nombre;
    private final String apellido;
    private final String numero;
    private final char sexo;
    private final String rol;
    private final String estado;

    public UsuarioDto(Usuario usuario) {
        this.nombreUsua = usuario.getNombreUsua();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.dni = usuario.getDni();
        this.numero = usuario.getNumero();
        this.sexo = usuario.getSexo();
        this.rol = usuario.getRol().getNombreRol();
        this.estado = usuario.getEstado().getNombreEstado();
    }

    public String getDni() {
        return dni;
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

    public String getEstado() {
        return estado;
    }
    
}
