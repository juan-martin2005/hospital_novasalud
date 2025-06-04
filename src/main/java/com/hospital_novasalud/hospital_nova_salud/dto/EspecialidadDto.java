package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;

public class EspecialidadDto {
    private final Long id;
    private final String nombre;
    private final String descripcion;

    public EspecialidadDto(Especialidad especialidad) {
        this.id = especialidad.getId();
        this.nombre = especialidad.getNombre();
        this.descripcion = especialidad.getDescripcion();
    }

    public Long getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
}
