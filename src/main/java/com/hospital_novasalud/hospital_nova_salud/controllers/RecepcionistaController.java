package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.RecepcionistaDto;
import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;
import com.hospital_novasalud.hospital_nova_salud.services.IRecepcionistaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recepcionista")
public class RecepcionistaController {

    @Autowired
    IRecepcionistaService recepcionistaService;
    @GetMapping("/listar")
    public List<RecepcionistaDto> listarRecepcionistas() {
        return recepcionistaService.findAll();
    }
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarRecepcionista(@Valid @RequestBody Recepcionista recepcionista, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        boolean existe = recepcionistaService.save(recepcionista);
        if(!existe) return ResponseEntity.status(HttpStatus.CREATED).body("Se registro al recepcionista con exito");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario para este recepcionista ya existe");
        
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){

        Optional<Recepcionista> recepcionista = recepcionistaService.findRecepcionistaById(id);
        if (recepcionista.isEmpty() || recepcionista.get().getUsuario().getEstado().getId() == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recepcionista no encontrado");
        }

        recepcionistaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Recepcionista eliminado con exito");
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
            err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
