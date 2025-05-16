package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hospital_novasalud.hospital_nova_salud.services.IPacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    @Autowired
    IPacienteService pacienteService;

    @GetMapping("/listar")
    public List<PacienteDto> listarRecepcionistas() {
        return pacienteService.findAll();
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarRecepcionista(@Valid @RequestBody Paciente paciente, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return pacienteService.save(paciente);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        return pacienteService.deleteById(id);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
                err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
