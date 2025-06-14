package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hospital_novasalud.hospital_nova_salud.resultEnum.Validaciones;
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
        Map<String, String> mensaje = new HashMap<>();
        Validaciones registrarRecepcionista = recepcionistaService.save(recepcionista);
        switch (registrarRecepcionista) {
            case YA_EXISTE:
                mensaje.put("error", "El nombre de usuario para este recepcionista ya existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
            case OK:
                mensaje.put("mensaje", "Se registro al recepcionista con exito");
                return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
            default:
                mensaje.put("error", "Ha ocurrido un error inesperado");
                return ResponseEntity.internalServerError().body(mensaje);  
        }
        
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){

        Map<String, String> mensaje = new HashMap<>();
        Validaciones eliminar = recepcionistaService.deleteById(id);
        switch (eliminar) {
            case USUARIO_NO_ENCONTRADO:
                mensaje.put("error", "Recepcionista no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            case OK:
                mensaje.put("error", "Recepcionista eliminado con exito");
                return ResponseEntity.status(HttpStatus.OK).body(mensaje);
            default:
                mensaje.put("error", "Ha ocurrido un error inesperado");
                return ResponseEntity.internalServerError().body(mensaje); 
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
            err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
