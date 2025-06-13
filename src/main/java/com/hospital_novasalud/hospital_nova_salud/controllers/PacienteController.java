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
    public List<PacienteDto> listarPaciente() {
        return pacienteService.findAll();
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPaciente(@Valid @RequestBody Paciente paciente, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        boolean existe = pacienteService.save(paciente);
        Map<String, String> response = new HashMap<>();
        if (!existe) {
            response.put("mensaje", "Se registró con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        response.put("error", "El paciente ya existe en el sistema");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.findPacienteById(id);
        if (paciente.isEmpty() || paciente.get().getUsuario().getEstado().getId() == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado");
        }

        pacienteService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Paciente eliminado con exito");
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
                err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
