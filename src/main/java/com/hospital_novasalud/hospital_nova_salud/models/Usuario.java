package com.hospital_novasalud.hospital_nova_salud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombreUsua;
    @NotBlank
    private String contrasena;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    @Size(min=7, max=8)
    private String dni;
    @NotBlank
    @Size(min=9, max=9)
    private String numero;
    @NotNull
    private char sexo;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;
    
    public Usuario() {
    }

    public Usuario(Long id, String nombreUsua, String contrasena, String nombre, String apellido, String dni,
            String numero, Character sexo, Rol rol, Estado estado) {
        this.id = id;
        this.nombreUsua = nombreUsua;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.numero = numero;
        this.sexo = sexo;
        this.rol = rol;
        this.estado = estado;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getNombreUsua() {
        return nombreUsua;
    }

    public void setNombreUsua(String nombreUsua) {
        this.nombreUsua = nombreUsua;
    }
    
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
}
