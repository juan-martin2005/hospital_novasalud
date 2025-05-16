package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

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
        return usuarioRepository.findAllByEstadoId(1).stream().map(UsuarioDto::new).toList();
    }
    
    @Override
    public ResponseEntity<?> save(Usuario us) {
        
        if(!existsByNombreUsuario(us.getNombreUsua())){
            
            Rol rol = rolRepository.findById(us.getRol().getId());
            Estado estado = estadoRepository.findById(1); //1=Activo, 2=Inactivo       
            Usuario usuario = new Usuario();
            usuario.setNombreUsua(us.getNombreUsua());
            usuario.setContrasena(us.getContrasena());
            usuario.setNombre(us.getNombre());
            usuario.setApellido(us.getApellido());
            usuario.setDni(us.getDni());
            usuario.setNumero(us.getNumero());
            usuario.setSexo(us.getSexo());
            usuario.setRol(rol);
            usuario.setEstado(estado);
            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
        }else return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty() || usuario.get().getEstado().getId() == 2){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        Estado estado = estadoRepository.findById(2); //1=Activo, 2=Inactivo
        Usuario user = usuario.orElseThrow();
        user.setEstado(estado);
        usuarioRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado con exito");
    }

    @Override
    public boolean existsByNombreUsuario(String n) {
        return usuarioRepository.existsByNombreUsua(n);
    }

}
