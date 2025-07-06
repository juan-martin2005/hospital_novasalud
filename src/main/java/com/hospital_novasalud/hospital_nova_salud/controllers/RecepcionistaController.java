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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.MedicamentoDto;
import com.hospital_novasalud.hospital_nova_salud.dto.RecepcionistaDto;
import com.hospital_novasalud.hospital_nova_salud.services.IMedicamentoService;
import com.hospital_novasalud.hospital_nova_salud.services.IRecepcionistaService;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarCampos;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recepcionista")
public class RecepcionistaController {

    @Autowired
    IRecepcionistaService recepcionistaService;
    @GetMapping("/listar")
    public List<RecepcionistaDto> listarRecepcionistas() {
        return recepcionistaService.findAll();
    }
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarRecepcionista(@Valid @RequestBody RecepcionistaDto recepcionista, BindingResult result) {
        Map<String, String> mensaje = new HashMap<>();
        int status = 0;
        try {
            if (result.hasFieldErrors()) {
                return ValidarCampos.validation(result);
            }
            Validaciones registrarRecepcionista = recepcionistaService.save(recepcionista);
            switch (registrarRecepcionista) {
                case YA_EXISTE:
                    mensaje.put("error", "El nombre de usuario para este recepcionista ya existe");
                    status = 400; break;
                case OK:
                    mensaje.put("mensaje", "Se registro al recepcionista con exito");
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
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){

        Map<String, String> mensaje = new HashMap<>();
        Validaciones eliminar = recepcionistaService.deleteById(id);
        switch (eliminar) {
            case USUARIO_NO_ENCONTRADO:
                mensaje.put("error", "Recepcionista no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            case OK:
                mensaje.put("error", "Recepcionista eliminado con exito");
                return ResponseEntity.status(HttpStatus.OK).body(mensaje);
            default:
                mensaje.put("error", "Ha ocurrido un error inesperado");
                return ResponseEntity.internalServerError().body(mensaje); 
        }
    }
    @Autowired
    IMedicamentoService medicamentoService;

    @GetMapping("/medicamento/listar")
    ResponseEntity<List<?>> findAllMedicamentos(){
        return new ResponseEntity<>(this.medicamentoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/medicamento/listar/{id}")
    ResponseEntity<?> findMedicamentoById(@PathVariable Long id){
        return new ResponseEntity<>(this.medicamentoService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/medicamento/crear")
    ResponseEntity<?> addMedicamento(@Valid @RequestBody MedicamentoDto medicamentoDTO, BindingResult result){
        if(result.hasFieldErrors()){
            return ValidarCampos.validation(result);
        }
        return new ResponseEntity<>(this.medicamentoService.addMedicamento(medicamentoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/medicamento/actualizar/{id}")
    ResponseEntity<?> updateMedicamento(@Valid @RequestBody MedicamentoDto medicamentoDTO, @PathVariable Long id, BindingResult result){
        if (result.hasFieldErrors()) {
            return ValidarCampos.validation(result);
        }
        return new ResponseEntity<>(this.medicamentoService.updateMedicamento(medicamentoDTO, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/medicamento/eliminar/{id}")
    ResponseEntity<String> deleteMedicamento(@PathVariable Long id){
        return new ResponseEntity<>(this.medicamentoService.deleteMedicamento(id), HttpStatus.OK);
    }
}
