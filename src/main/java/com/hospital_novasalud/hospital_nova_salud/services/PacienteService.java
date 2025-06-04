package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.PacienteDto;
import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEstadoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IPacienteRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

@Service
public class PacienteService implements IPacienteService{

    @Autowired
    private IPacienteRepository pacienteRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IRolRepository rolRepository;
    @Autowired
    private IEstadoRepository estadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<PacienteDto> findAll() {
        return pacienteRepository.findByUsuario_EstadoId(1).stream().map(PacienteDto::new).toList();
    }

    
    @Override
    public boolean save(Paciente pa) {
        boolean existe = pacienteRepository.existsByDni(pa.getDni());
        Rol rol = rolRepository.findByNombreRol("ROL_PACIENTE");
        Estado estadoActivo = estadoRepository.findById(1).orElseThrow();
        Paciente paciente = new Paciente();
        Usuario usuario = new Usuario();

        if(!existe){
            paciente.setDni(pa.getDni());
            usuario.setContrasena(passwordEncoder.encode(pa.getUsuario().getContrasena()));
            usuario.setNombre(pa.getUsuario().getNombre());
            usuario.setNombreUsua(paciente.getDni());
            usuario.setApellido(pa.getUsuario().getApellido());
            usuario.setNumero(pa.getUsuario().getNumero());
            usuario.setSexo(pa.getUsuario().getSexo());
            usuario.setEstado(estadoActivo);
            usuario.setRol(rol);
            usuario = usuarioRepository.save(usuario);
    
            paciente.setUsuario(usuario);
            pacienteRepository.save(paciente);
        }
        return existe;
    }
    @Override
    public void deleteById(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        Estado estado = estadoRepository.findById(2).orElseThrow(); //1=Activo, 2=Inactivo
        Paciente pac = paciente.orElseThrow();
        pac.getUsuario().setEstado(estado);
        pacienteRepository.save(pac);
    }


    @Override
    public Optional<Paciente> findPacienteById(Long id) {
        return pacienteRepository.findById(id);
    }
}
