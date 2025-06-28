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

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.dto.DoctorEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.HorarioDoctorDto;
import com.hospital_novasalud.hospital_nova_salud.services.IDoctorService;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarCampos;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    IDoctorService doctorService;

    @GetMapping("/listar")
    public List<DoctorEnvioDto> listarDoctores(){
        return doctorService.findAll();
    }
    @GetMapping("/listar-especialidad/{id}")
    public List<DoctorDto> listarDoctoresPorEspecialidad(@PathVariable Long id){
        return doctorService.findByEspecialidad(id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDoctor(@Valid @RequestBody DoctorDto doctor, BindingResult result){
        Map<String, String> mensaje = new HashMap<>();
        int status = 0;
        try {
            if(result.hasFieldErrors()) {
                return ValidarCampos.validation(result);
            }
            Validaciones registro = doctorService.save(doctor);
            switch (registro) {
                case YA_EXISTE:
                    mensaje.put("Error", "El nombre de usuario para este doctor ya existe");
                    status = 400; break;
                case ESPECIALIDAD_NO_ENCONTRADA:
                    mensaje.put("Error", "No se encontró la especialidad");
                    status = 400; break;
                case OK:
                    mensaje.put("Mensaje", "Se registro al doctor con exito");
                    status = 201; break;
                default:
                    mensaje.put("Error", "No se puedo realizar la operacion");
                    status = 500; break;
            }
            return ResponseEntity.status(status).body(mensaje);
            
        } catch (Exception e) {
            mensaje.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(mensaje);
        }

    }

    @PostMapping("/agregar-horario")
    public ResponseEntity<?> modificarHorarioDoctor(@Valid @RequestBody HorarioDoctorDto horario, BindingResult result){
        Map<String, String> mensaje = new HashMap<>();
        int status = 0;
        try {
            if(result.hasFieldErrors()) {
                return ValidarCampos.validation(result);
            }
            ValidarHorario registro = doctorService.registrarHorario(horario);

            switch (registro) {
            case OK:
                mensaje.put("mensaje","Se registró el horario del doctor con éxito");
                status = 201; break;
            case HORARIO_INVALIDO:
                mensaje.put("mensaje","El horario de atención es invalido");
                status = 400; break;
            case HORARIO_EN_USO:
                mensaje.put("mensaje","El horario de atención ya está en uso");
                status = 400; break;
            case FECHA_INVALIDA:
                mensaje.put("mensaje","No se puede asignar un horario en una fecha pasada");
                status = 400; break;
            default:
                mensaje.put("mensaje","Ocurrió un error inesperado");
                status = 500; break;
            }
            return ResponseEntity.status(status).body(mensaje);
        } catch (Exception e) {
            mensaje.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(mensaje);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        Map<String,String> mensaje = new HashMap<>();
        int status = 0;
        Validaciones eliminar = doctorService.deleteById(id);
        switch (eliminar) {
            case USUARIO_NO_ENCONTRADO:
                mensaje.put("error","Doctor no encontrado");
                status = 404; break;
            case OK: 
                mensaje.put("error","Doctor eliminado correctamente");
                status = 200; break;
            default:
                mensaje.put("error", "No se puedo realizar esta accion");
                status = 500; break;
            }
            return ResponseEntity.status(status).body(mensaje);     
        }
}
