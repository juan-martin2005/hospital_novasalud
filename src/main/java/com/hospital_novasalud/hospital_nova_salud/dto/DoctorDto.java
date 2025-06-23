package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Doctor;

import jakarta.validation.constraints.NotNull;

public class DoctorDto extends UsuarioDto{
    @NotNull
    private Long especialidad;
    
    public DoctorDto(){ }
    
    public DoctorDto(Doctor doc){
        super(doc.getUsuario());
        this.especialidad = doc.getEspecialidad().getId();
    }

    public Long getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Long especialidad) {
        this.especialidad = especialidad;
    }
   
}
