package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.services.ICitaMedicaService;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarCampos;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cita-medica")
public class CitaMedicaController {

    @Autowired
    private ICitaMedicaService citaMedicaService;

    @GetMapping("/listar")
    public List<CitaMedicaEnvioDto> listarCitas() {
        return citaMedicaService.findAll();
    }
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCita(@Valid @RequestBody CitaMedicaDto citaMedica, BindingResult result){
        Map<String, String> mensaje = new HashMap<>();
        int status = 0;
        try {
            if (result.hasFieldErrors()) {
                return ValidarCampos.validation(result);
            }
            ValidarHorario citaDisponible = citaMedicaService.save(citaMedica);
            switch (citaDisponible) {
                case OK:
                    mensaje.put("mensaje", "Cita médica registrada correctamente");
                    status = 201; break;
                case FECHA_INVALIDA:
                    mensaje.put("error", "Fecha ingresada es inválido");
                    status = 400; break;
                case HORARIO_INVALIDO:
                    mensaje.put("error", "Hora ingresada es inválido");
                    status = 400; break;
                case HORARIO_NO_DISPONIBLE:
                    mensaje.put("error", "Horario no disponible");
                    status = 400; break;
                case HORARIO_EN_USO:
                    mensaje.put("error", "Horario para esta cita no se encuentra disponible");
                    status = 400; break;
                case ERROR:
                    mensaje.put("error", "Doctor no se encontró");
                    status = 400; break;
            }
            return ResponseEntity.status(status).body(mensaje);
        } catch (Exception e) {
            mensaje.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(mensaje);
        }

    }
}
