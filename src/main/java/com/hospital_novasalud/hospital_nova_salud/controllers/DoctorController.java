package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.ValidacionHorario;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.Validaciones;
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
        Map<String, String> mensaje = new HashMap<>();
        Validaciones registro = doctorService.save(doctor);
        switch (registro) {
            case YA_EXISTE:
            mensaje.put("Error", "El nombre de usuario para este doctor ya existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
            case ESPECIALIDAD_NO_ENCONTRADA:
            mensaje.put("Error", "No se encontró la especialidad");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            case OK:
            mensaje.put("Mensaje", "Se registro al doctor con exito");
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
            default:
            mensaje.put("Error", "No se puedo realizar la operacion");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);

        }

    }

    @PostMapping("/agregar-horario")
    public ResponseEntity<?> modificarHorarioDoctor(@Valid @RequestBody HorarioDoctor horario, BindingResult result){
        if(result.hasFieldErrors()) {
            return validation(result);
        }
        ValidacionHorario registro = doctorService.updateHorario(horario);

        switch (registro) {
        case OK:
            return ResponseEntity.status(HttpStatus.CREATED).body("Se registró el horario del doctor con éxito");
        case HORARIO_INVALIDO:
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El horario de atención es invalido");
        case HORARIO_EN_USO:
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El horario de atención ya está en uso");
        case FECHA_INVALIDA:
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede asignar un horario en una fecha pasada");
        default:
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado");
    }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        Map<String,String> mensaje = new HashMap<>();
        Validaciones eliminar = doctorService.deleteById(id);
        switch (eliminar) {
            case USUARIO_NO_ENCONTRADO:
                mensaje.put("error","Doctor no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);       
            case OK: 
                mensaje.put("error","Doctor eliminado correctamente");
                return ResponseEntity.status(HttpStatus.OK).body(mensaje);       
            default:
                mensaje.put("error", "No se puedo realizar esta accion");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);       
        }
    }
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(
            err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
