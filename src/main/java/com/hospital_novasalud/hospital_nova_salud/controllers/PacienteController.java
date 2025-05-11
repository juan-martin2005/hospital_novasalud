package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.services.IPacienteService;

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
    public ResponseEntity<?> registrarRecepcionista(@RequestBody Paciente paciente) {
        return pacienteService.save(paciente);
    }
}
