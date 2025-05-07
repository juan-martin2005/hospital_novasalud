package com.hospital_novasalud.hospital_nova_salud.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.services.IUsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    IUsuarioService usuarioService;
    @GetMapping("/listar")
    public Iterable<Usuario> listarUsuarios() {
        return usuarioService.findAll();
    }
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuarios(@RequestParam String nombreUsuario, @RequestParam String contrasena, @RequestParam int rolU) {
        
        return usuarioService.save(nombreUsuario, contrasena, rolU);   

    }
}
