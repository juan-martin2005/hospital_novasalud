package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IPacienteRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

@Service
public class PacienteService implements IPacienteService{

    @Autowired
    IPacienteRepository pacienteRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IRolRepository rolRepository;
    @Override
    public List<PacienteDto> findAll() {
        return pacienteRepository.findAll().stream().map(PacienteDto::new).toList();
    }

    
    @Override
    public ResponseEntity<?> save(Paciente pa) {
        Usuario usuario = usuarioRepository.findById(pa.getUsuario().getId()).orElse(null);
        Rol rol = rolRepository.findById(4);
        if(usuario == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el usuario");
        }

        if(pa.getId() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        if(existsByUsuarioId(pa.getUsuario().getId()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente ya existe");
        }
        if(rol != usuario.getRol()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No es paciente");
        }
        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        pacienteRepository.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se registro el paciente con exito");
    }
    
    @Override
    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsuarioId(Long id) {
        return pacienteRepository.existsByUsuarioId(id);
    }

}
