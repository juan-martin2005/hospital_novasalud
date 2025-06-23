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

import com.hospital_novasalud.hospital_nova_salud.dto.EspecialidadDto;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.services.IEspecialidadesService;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarCampos;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private IEspecialidadesService especialidadesService;
    
    @GetMapping("/listar")
    public List<EspecialidadDto> listarEspecialidades() {
        return especialidadesService.findAll();
    }
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEspecialidad(@Valid @RequestBody Especialidad especialidad, BindingResult result) {
        if(result.hasFieldErrors()) {
            return ValidarCampos.validation(result);
        }
        Map<String, String> mensaje = new HashMap<>();
        Validaciones existeEspecialidad = especialidadesService.save(especialidad);
        switch (existeEspecialidad) {
            case YA_EXISTE:
                mensaje.put("error", "La especialidad ya existe");
                return ResponseEntity.badRequest().body(mensaje);
            case OK:
                mensaje.put("mensaje", "Especialidad registrada correctamente");
                return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
            default:
                mensaje.put("error", "Ha ocurrido un error inesperado");
                return ResponseEntity.internalServerError().body(mensaje);

        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEspecialidad(@PathVariable Long id) {
        Map<String, String> mensaje = new HashMap<>();
        Validaciones eliminar = especialidadesService.deleteById(id);
        switch (eliminar) {
            case ESPECIALIDAD_NO_ENCONTRADA:
                mensaje.put("error", "La especialidad seleccionada no existe");
                return ResponseEntity.badRequest().body(mensaje);
            case OK:
                mensaje.put("mensaje", "Especialidad eliminada correctamente");
                return ResponseEntity.ok(mensaje);
            default:
                mensaje.put("error", "Ha ocurrido un error inesperado");
                return ResponseEntity.internalServerError().body(mensaje);
        }
    }
}
