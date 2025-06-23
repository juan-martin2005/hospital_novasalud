package com.hospital_novasalud.hospital_nova_salud.models;


import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="doctores")
public class Doctor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<RecetaMedica> recetaMedica;
    
    public Doctor() {
    }
       
    public Doctor(Usuario usuario, Especialidad especialidad, Set<RecetaMedica> recetaMedica) {
        this.usuario = usuario;
        this.especialidad = especialidad;
        this.recetaMedica = recetaMedica;
    }



    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Especialidad getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Set<RecetaMedica> getRecetaMedica() {
        return recetaMedica;
    }
    
    public void setRecetaMedica(Set<RecetaMedica> recetaMedica) {
        this.recetaMedica = recetaMedica;
    }
    public RecetaMedica generarReceta(Doctor d, Paciente p, Medicamento m, String mensaje){
        RecetaMedica recetaMedica = new RecetaMedica();
        LocalDateTime fechaCreada = LocalDateTime.now();
        recetaMedica.setDoctor(d);
        recetaMedica.setPaciente(p);
        recetaMedica.setMedicamento(m);
        recetaMedica.setMensaje(mensaje);
        recetaMedica.setFechaCreada(fechaCreada);
        return recetaMedica;
    }
}
