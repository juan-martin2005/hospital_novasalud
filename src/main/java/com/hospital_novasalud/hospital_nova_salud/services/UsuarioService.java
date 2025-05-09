package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.UsuarioDto;
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
    public List<UsuarioDto> findAll() {
        return usuarioRepository.findAll().stream().map(UsuarioDto::new).toList();
    }
    
    @Override
    public ResponseEntity<?> save(Usuario us) {
        Map<String, String> response = new HashMap<>();
        
        if(!existsByNombreUsuario(us.getNombreUsua())){

            Usuario usuario = new Usuario();
            usuario.setNombreUsua(us.getNombreUsua());
            usuario.setContrasena(us.getContrasena());
            usuario.setNombre(us.getNombre());
            usuario.setDni(us.getDni());
            usuario.setNumero(us.getNumero());
            usuario.setSexo(us.getSexo());
            Rol rol = rolRepository.findById(us.getRol().getId());
            Estado estado = estadoRepository.findById(1); //1=Activo, 2=Inactivo       
            usuario.setRol(rol);
            usuario.setEstado(estado);
            response.put("mensaje", "Usuario creado con exito");
            usuarioRepository.save(usuario);
        }else response.put("error","El usuario ya existe");
        return response.containsKey("error") ? ResponseEntity.status(HttpStatus.CONFLICT).body(response) : ResponseEntity.status(HttpStatus.CREATED).body(response);
        
    }
    @Override
    public boolean existsByNombreUsuarioAndContrasena(String n, String c) {
        
        return usuarioRepository.existsByNombreUsuaAndContrasena(n, c);
    }


    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombreUsuario(String n) {
        return usuarioRepository.existsByNombreUsua(n);
    }



}
