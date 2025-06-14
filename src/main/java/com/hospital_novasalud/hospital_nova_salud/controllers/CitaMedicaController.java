package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.ValidacionHorario;
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
        Map<String, String> mensaje = new HashMap<>();
        ValidacionHorario citaDisponible = citaMedicaService.save(citaMedica);
        switch (citaDisponible) {
            case OK:
                mensaje.put("mensaje", "Cita médica registrada correctamente");
                return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);   
            case FECHA_INVALIDA:
                mensaje.put("error", "Fecha ingresada es inválido");
                return ResponseEntity.badRequest().body(mensaje);   
            case HORARIO_INVALIDO:
                mensaje.put("error", "Hora ingresada es inválido");
                return ResponseEntity.badRequest().body(mensaje);   
            case HORARIO_NO_DISPONIBLE:
                mensaje.put("error", "Horario no disponible");
                return ResponseEntity.badRequest().body(mensaje);
            case HORARIO_EN_USO:
                mensaje.put("error", "Horario para esta cita no se encuentra disponible");
                return ResponseEntity.badRequest().body(mensaje);   
            case ERROR:
                mensaje.put("error", "Doctor no se encontró");
                return ResponseEntity.badRequest().body(mensaje);
            default:
                mensaje.put("error", "Ocurrio un error");
                return ResponseEntity.internalServerError().body(mensaje);
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
