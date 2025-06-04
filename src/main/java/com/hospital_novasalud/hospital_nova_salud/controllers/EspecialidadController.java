package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.EspecialidadDto;
import com.hospital_novasalud.hospital_nova_salud.services.IEspecialidadesService;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private IEspecialidadesService especialidadesService;
    
    @GetMapping("/listar")
    public List<EspecialidadDto> listarEspecialidades() {
        return especialidadesService.findAll();
    }
}
