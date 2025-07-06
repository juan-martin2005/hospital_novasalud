package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import com.hospital_novasalud.hospital_nova_salud.dto.BoletaDto;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IBoletaRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IPacienteRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

public class BoletaService implements IBoletaService {
     @Autowired
    private IBoletaRepository boletaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Override
    public List<BoletaDto> findBoletasByPaciente() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(authentication.getName()).orElseThrow();

        Paciente paciente = pacienteRepository.findByUsuarioId(usuario.getId());

        return boletaRepository
            .findAll()
            .stream()
            .filter(b -> b.getPaciente().getId().equals(paciente.getId()))
            .map(BoletaDto::new)
            .toList();
    }
}
