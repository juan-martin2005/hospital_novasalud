package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.services.ICitaMedicaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cita-medica")
public class CitaMedicaController {

    @Autowired
    private ICitaMedicaService citaMedicaService;

    @GetMapping("/listar")
    public List<CitaMedicaDto> listarCitas() {
        return citaMedicaService.findAll();
    }
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCita(@Valid @RequestBody CitaMedica citaMedica, BindingResult result){
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        boolean citaDisponible = citaMedicaService.save(citaMedica);
        if(citaDisponible) return ResponseEntity.ok("Cita médica registrada correctamente");
        return ResponseEntity.badRequest().body("Doctor o Horario no disponible, elija otro horario");
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
            err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
