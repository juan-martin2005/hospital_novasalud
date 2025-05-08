package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;

public class UsuarioDto {
    private Long id;
    private String nombreUsua;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String dni;
    private String numero;
    private char sexo;
    private int rol;
    private int estado;
    public UsuarioDto() {
       
    }
    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nombreUsua = usuario.getNombreUsua();
        this.contrasena = usuario.getContrasena();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.dni = usuario.getDni();
        this.numero = usuario.getNumero();
        this.sexo = usuario.getSexo();
        this.rol = usuario.getRol().getId();
        this.estado = usuario.getEstado().getId();
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
    public int getRol() {
        return rol;
    }
    public void setRol(int rol) {
        this.rol = rol;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }


    
}
