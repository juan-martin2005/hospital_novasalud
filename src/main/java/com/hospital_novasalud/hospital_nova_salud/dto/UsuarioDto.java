package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

import jakarta.validation.constraints.NotBlank;

public class UsuarioDto extends DatosBasicosDto{

    @NotBlank
    private String username;
    @NotBlank
    private String contrasena;
    
    public UsuarioDto() {
    }

    public UsuarioDto(Usuario usuario) {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
