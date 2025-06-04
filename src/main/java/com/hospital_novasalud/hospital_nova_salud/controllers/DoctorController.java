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

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.services.IDoctorService;

import jakarta.validation.Valid;

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
    public ResponseEntity<?> registrarDoctor(@Valid @RequestBody Doctor doctor, BindingResult result){
        if(result.hasFieldErrors()) {
            return validation(result);
        }
        boolean existe = doctorService.save(doctor);
        Especialidad especialidad = doctorService.findEspecialidad(doctor);

        if(especialidad == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la especialidad");
        if(!existe) return ResponseEntity.status(HttpStatus.CREATED).body("Se registro al doctor con exito");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario para este doctor ya existe");

    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        Optional<Doctor> doctor = doctorService.findDoctorById(id);
        if(doctor.isEmpty() || doctor.get().getUsuario().getEstado().getId() == 2){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor no encontrado");
        }
        
        doctorService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Doctor eliminado con exito");
    }
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
            err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
