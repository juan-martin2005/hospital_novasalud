package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.services.IDoctorService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    IDoctorService doctorService;

    @GetMapping("/listar")
    public List<DoctorDto> listarDoctores(){
        return doctorService.findAll();
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDoctor(@RequestBody Doctor doctor){
        return doctorService.save(doctor);
    }
}
