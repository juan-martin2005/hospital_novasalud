package com.hospital_novasalud.hospital_nova_salud.controllers;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.dto.UsuarioDto;
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
    public ResponseEntity<?> registrarUsuarios(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);   
    }
}
