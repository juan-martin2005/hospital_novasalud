package com.hospital_novasalud.hospital_nova_salud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.BoletaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.services.CitaMedicaService;

@RestController
@RequestMapping("api/paciente")
public class CitaMedicaController {
    @Autowired
    private CitaMedicaService citaMedicaService;
    @PostMapping("/cita/{id}/boleta")
        public ResponseEntity<BoletaDto> registrarBoletaParaCita(@PathVariable Long id) {
        BoletaDto boleta = citaMedicaService.generarBoletaParaCita(id);
        return new ResponseEntity<>(boleta, HttpStatus.CREATED);
    }
    @PostMapping("/registrar-cita-con-id")
    public ResponseEntity<CitaMedicaEnvioDto> registrarCitaConId(@RequestBody CitaMedicaDto citaDto) {
        CitaMedicaEnvioDto citaRegistrada = citaMedicaService.saveAndReturnDto(citaDto);
        return new ResponseEntity<>(citaRegistrada, HttpStatus.CREATED);
    }

}
