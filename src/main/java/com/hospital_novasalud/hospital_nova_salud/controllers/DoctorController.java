package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/listar-especialidad/{id}")
    public List<DoctorDto> listarDoctoresPorEspecialidad(@PathVariable Long id){
        return doctorService.findByEspecialidad(id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDoctor(@RequestBody Doctor doctor){
        return doctorService.save(doctor);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        doctorService.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }
}
