package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital_novasalud.hospital_nova_salud.dto.BoletaDto;
import com.hospital_novasalud.hospital_nova_salud.services.IBoletaService;

public class BoletaController {
    @Autowired
    private IBoletaService boletaService;

    @GetMapping("/listar")
    public ResponseEntity<List<BoletaDto>> listarBoletasPaciente() {
        return ResponseEntity.ok(boletaService.findBoletasByPaciente());
    }
}
