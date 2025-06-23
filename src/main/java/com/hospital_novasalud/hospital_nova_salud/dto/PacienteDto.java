package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PacienteDto extends DatosBasicosDto{
    @NotBlank
    @Size(min = 8, max = 8, message = "debe de tener 8 digitos")
    @Pattern(regexp = "^[0-9]+$",message = "tiene un formato incorrecto")
    private String dni;
    @NotBlank
    private String contrasena;
    public PacienteDto() {
    }

    public PacienteDto(Paciente pa){
        super(pa.getUsuario());
        this.dni = pa.getDni();
    }

    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
}
