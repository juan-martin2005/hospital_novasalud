package com.hospital_novasalud.hospital_nova_salud.models;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    private Date horarioAtencion;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<RecetaMedica> recetaMedica;
    public Doctor() {
    }

    public Doctor(Long id, Especialidad especialidad, Usuario usuario, Date horarioAtencion) {
        this.id = id;
        this.especialidad = especialidad;
        this.usuario = usuario;
        this.horarioAtencion = horarioAtencion;
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

    public Date getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(Date horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public void registrarHorario(){
        
    }
    public RecetaMedica generarReceta(Doctor d, Paciente p, Medicamento m, String dosis, String frecuencia){
        RecetaMedica recetaMedica = new RecetaMedica();
        LocalDateTime fechaCreada = LocalDateTime.now();
        DateTimeFormatter formatear = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fecha = fechaCreada.format(formatear);
        recetaMedica.setDoctor(d);
        recetaMedica.setPaciente(p);
        recetaMedica.setMedicamento(m);
        recetaMedica.setDosis(dosis);
        recetaMedica.setFrecuencia(frecuencia);
        recetaMedica.setFechaCreada(fecha);
        return recetaMedica;
    }
}
