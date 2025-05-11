package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.RecepcionistaDto;
import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRecepcionistaRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

@Service
public class RecepcionistaService implements IRecepcionistaService{

    @Autowired
    IRecepcionistaRepository recepcionistaRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IRolRepository rolRepository;

    @Override
    public List<RecepcionistaDto> findAll() {
        return recepcionistaRepository.findAll().stream().map(RecepcionistaDto::new).toList();
    }

    @Override
    public RecepcionistaDto findById(Long id) {
        return recepcionistaRepository.findById(id).map(RecepcionistaDto::new).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Recepcionista re) {
        Usuario usuario = usuarioRepository.findById(re.getUsuario().getId()).orElse(null);

        if(usuario == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el usuario");
        }

        if(re.getId() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no es recepcionista");
        }
        if(existsByUsuarioId(re.getUsuario().getId()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El recepcionista ya existe");
        }
        // if(){
        //    Logica para saber si el usario es distinto al rol de recepcionista
        // }
        Recepcionista recepcionista = new Recepcionista();
        recepcionista.setUsuario(usuario);
        recepcionistaRepository.save(recepcionista);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se registro el recepcionista con exito");
        
    }

    @Override
    public boolean existsByUsuarioId(Long id) {
        return recepcionistaRepository.existsByUsuarioId(id);
    }

}
