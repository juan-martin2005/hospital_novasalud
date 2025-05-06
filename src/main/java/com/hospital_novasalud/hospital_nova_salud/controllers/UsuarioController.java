package com.hospital_novasalud.hospital_nova_salud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEstadoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IRolRepository rolRepository;
    @Autowired
    IEstadoRepository estadoRepository;
    @GetMapping("/listar")
    public Iterable<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    @PostMapping("/registrar")
    public Usuario registrarUsuarios(@RequestParam String nombreUsuario, @RequestParam String contrasena) {
        Rol rol = rolRepository.findByNombreRol("Paciente");
        Estado estado = estadoRepository.findByNombreEstado("Activo");
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasena(contrasena);
        usuario.setRol(rol);
        usuario.setEstado(estado);
        
        return usuarioRepository.save(usuario);
    }
}
