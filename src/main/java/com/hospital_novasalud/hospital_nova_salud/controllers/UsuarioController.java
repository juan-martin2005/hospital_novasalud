package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.UsuarioDto;
import com.hospital_novasalud.hospital_nova_salud.services.IUsuarioService;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    IUsuarioService usuarioService;
    @GetMapping("/listar")
    public List<UsuarioDto> listarUsuarios() {
        return usuarioService.findAll();
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        
        Validaciones eliminar = usuarioService.deleteById(id);
        switch (eliminar) {
            case USUARIO_NO_ENCONTRADO:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            case OK:
                return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado con exito");
            default:
                return ResponseEntity.internalServerError().body(null);
        }
    }
}
