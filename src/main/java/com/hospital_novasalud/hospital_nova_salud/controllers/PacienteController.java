package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.HorarioDoctorEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.dto.PacienteEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.services.ICitaMedicaService;
import com.hospital_novasalud.hospital_nova_salud.services.IPacienteService;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarCampos;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private ICitaMedicaService citaMedicaService;
    @GetMapping("/listar")
    public List<PacienteEnvioDto> listarPaciente() {
        return pacienteService.findAll();
    }

    @GetMapping("/mis-citas")
    public List<CitaMedicaEnvioDto> listarCitas() {
        return citaMedicaService.findByPaciente();
    }
    @GetMapping("/listar-horario")
    public List<HorarioDoctorEnvioDto> listarHorarioDoctor() {
        return pacienteService.findHorarioDoctor();
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPaciente(@Valid @RequestBody PacienteDto paciente, BindingResult result) {
        Map<String, String> mensaje = new HashMap<>();
        int status = 0;
        try {
            if (result.hasFieldErrors()) {
                return ValidarCampos.validation(result);
            }
            Validaciones registrarPaciente = pacienteService.save(paciente);
            switch (registrarPaciente) {
                case YA_EXISTE:
                    mensaje.put("error", "El paciente ya existe en el sistema");
                    status = 400; break;
                case OK:
                    mensaje.put("mensaje", "Se registró con éxito");
                    status = 201; break;
                default:
                    mensaje.put("error", "Ha ocurrido un error inesperado");
                    status = 500; break;
            }
            return ResponseEntity.status(status).body(mensaje);
        } catch (Exception e) {
            mensaje.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(mensaje);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) {
        
        Map<String, String> mensaje = new HashMap<>();
        int status = 0;
        Validaciones eliminar = pacienteService.deleteById(id);
        switch (eliminar) {
            case USUARIO_NO_ENCONTRADO:
                mensaje.put("error", "Paciente no encontrado");
                status = 404; break;
            case OK:
                mensaje.put("mensaje", "Paciente eliminado con exito");
                status = 200; break;
            default:
                mensaje.put("error", "Ha ocurrido un error inesperado");
                status = 500; break;
            }
        
        return ResponseEntity.status(status).body(mensaje);
    }

     @PostMapping("/registrar-cita")
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
                case ERROR:
                    mensaje.put("error", "Doctor no se encontró");
                    status = 400; break;
                case HORARIO_NO_DISPONIBLE:
                    mensaje.put("error", "El horario no se encuentra disponible");
                    status = 400; break;
                case HORARIO_EN_USO:
                    mensaje.put("error", "El cita ya existe");
                    status = 400; break;
                default:
                    mensaje.put("error", "Ocurrió un error inesperado");
                    status = 500; break;
            }
            return ResponseEntity.status(status).body(mensaje);
        } catch (Exception e) {
            mensaje.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(mensaje);
        }

    }
}
