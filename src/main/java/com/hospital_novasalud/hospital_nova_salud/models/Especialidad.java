package com.hospital_novasalud.hospital_nova_salud.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="especialidades")   
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    @OneToMany(mappedBy = "especialidad")
    private List<Doctor> doctor;
    
    public Especialidad(Long id, String nombre, String descripcion, List<Doctor> doctor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.doctor = doctor;
    }
    public Especialidad() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Doctor> getDoctor() {
        return doctor;
    }

    public void setDoctor(List<Doctor> doctor) {
        this.doctor = doctor;
    }

    
}
