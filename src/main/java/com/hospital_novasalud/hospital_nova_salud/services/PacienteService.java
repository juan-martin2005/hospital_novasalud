package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IPacienteRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

public class PacienteService implements IPacienteService{

    @Autowired
    IPacienteRepository pacienteRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Override
    public List<PacienteDto> findAll() {
        return pacienteRepository.findAll().stream().map(PacienteDto::new).toList();
    }

    
    @Override
    public ResponseEntity<?> save(Paciente pa) {
        Usuario usuario = usuarioRepository.findById(pa.getUsuario().getId()).orElse(null);

        if(usuario == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el usuario");
        }

        if(pa.getId() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        if(existsByUsuarioId(pa.getUsuario().getId()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El recepcionista ya existe");
        }
        // if( ){
        // logica para saber si el usario es distinto al rol de paciente
        // }
        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        pacienteRepository.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se registro el paciente con exito");
    }
    
    @Override
    public void delete(Long id) {
        pacienteRepository.deleteById(id);
    }
    @Override
    public boolean existsByDni(String dni) {
        return pacienteRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByUsuarioId(Long id) {
        return pacienteRepository.existsByUsuarioId(id);
    }

}
