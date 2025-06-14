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

import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.Validaciones;
import com.hospital_novasalud.hospital_nova_salud.services.IPacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    @Autowired
    IPacienteService pacienteService;

    @GetMapping("/listar")
    public List<PacienteDto> listarPaciente() {
        return pacienteService.findAll();
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPaciente(@Valid @RequestBody Paciente paciente, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Map<String, String> mensaje = new HashMap<>();
        Validaciones registrarPaciente = pacienteService.save(paciente);
        switch (registrarPaciente) {
            case YA_EXISTE:
                mensaje.put("error", "El paciente ya existe en el sistema");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
            case OK:
                mensaje.put("mensaje", "Se registró con éxito");
                return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
            default:
                mensaje.put("error", "Ha ocurrido un error inesperado");
                return ResponseEntity.internalServerError().body(mensaje);       
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) {
        
        Map<String, String> mensaje = new HashMap<>();
        Validaciones eliminar = pacienteService.deleteById(id);
        switch (eliminar) {
            case USUARIO_NO_ENCONTRADO:
                mensaje.put("error", "Paciente no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            case OK:
                mensaje.put("mensaje", "Paciente eliminado con exito");
                return ResponseEntity.status(HttpStatus.OK).body(mensaje);
            default:
                mensaje.put("error", "Ha ocurrido un error inesperado");
                return ResponseEntity.internalServerError().body(mensaje);
        }

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
                err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
