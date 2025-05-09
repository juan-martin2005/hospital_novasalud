package com.hospital_novasalud.hospital_nova_salud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    private final static int cantidadCitas = 0;
    private final static int cantidadRecetas = 0;
    
    public Paciente() {
    }
    public Paciente(Long id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public static int getCantidadcitas() {
        return cantidadCitas;
    }
    public static int getCantidadrecetas() {
        return cantidadRecetas;
    }
    
}
