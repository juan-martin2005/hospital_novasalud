package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DatosBasicosDto {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @Size(min = 9, max = 9, message = "debe de tener 9 digitos")
    @Pattern(regexp = "^[0-9]+$",message = "tiene un formato incorrecto")
    @NotBlank
    private String numero;
    @NotNull
    private char sexo;
    public DatosBasicosDto() {
    }
    public DatosBasicosDto(Usuario usuario){
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.numero = usuario.getNumero();
        this.sexo = usuario.getSexo();
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
