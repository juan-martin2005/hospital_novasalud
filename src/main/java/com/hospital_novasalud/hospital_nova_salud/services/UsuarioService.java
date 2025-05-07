package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEstadoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;


@Service
public class UsuarioService implements IUsuarioService{
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IRolRepository rolRepository;
    @Autowired
    IEstadoRepository estadoRepository;
    
    @Override
    public Iterable<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    @Override
    public ResponseEntity<Map<String,String>> save(String n, String c, int r) {
        Map<String, String> mensaje = new HashMap<>();
        if(!existsByNombreUsuario(n)){
            usuarioRepository.existsByNombreUsuario(n);
            Usuario usuario = new Usuario();
            Rol rol = rolRepository.findById(r);
            Estado estado = estadoRepository.findById(1); //1=Activo, 2=Inactivo
            usuario.setNombreUsuario(n);
            usuario.setContrasena(c);
            usuario.setRol(rol);
            usuario.setEstado(estado);
            usuarioRepository.save(usuario);
            mensaje.put("mensaje","Usuario registrado correctamente");
        }else{ mensaje.put("error","El nombre de usuario ya existe");}
        return mensaje.containsKey("mensaje")? ResponseEntity.status(HttpStatus.CREATED).body(mensaje) : ResponseEntity.status(HttpStatus.CONFLICT).body(mensaje);
    }
    @Override
    public boolean existsByNombreUsuarioAndContrasena(String n, String c) {
        
        return usuarioRepository.existsByNombreUsuarioAndContrasena(n, c);
    }


    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombreUsuario(String n) {
        return usuarioRepository.existsByNombreUsuario(n);
    }


}
