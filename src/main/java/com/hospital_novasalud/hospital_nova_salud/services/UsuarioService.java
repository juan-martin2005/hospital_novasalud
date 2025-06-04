package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.UsuarioDto;
import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEstadoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;



@Service
public class UsuarioService implements IUsuarioService{
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IEstadoRepository estadoRepository;

    @Override
    public List<UsuarioDto> findAll() {
        return usuarioRepository.findAllByEstadoId(1).stream().map(UsuarioDto::new).toList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        Estado estado = estadoRepository.findById(2).orElseThrow(); //1=Activo, 2=Inactivo
        Usuario user = usuario.orElseThrow();
        user.setEstado(estado);
        usuarioRepository.save(user);
    }
    @Override
    public Optional<Usuario> findUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }
}
