package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    IPacienteRepository pacienteRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IRolRepository rolRepository;
    @Autowired
    IEstadoRepository estadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<PacienteDto> findAll() {
        return pacienteRepository.findByUsuario_EstadoId(1).stream().map(PacienteDto::new).toList();
    }

    
    @Override
    public ResponseEntity<?> save(Paciente pa) {
        Rol rol = rolRepository.findByNombreRol("ROL_PACIENTE");
        Paciente paciente = new Paciente();
        Usuario usuario = new Usuario();
        Estado estado = estadoRepository.findById(1);

        if(!pacienteRepository.existsByDni(pa.getDni())){

            paciente.setDni(pa.getDni());
            usuario.setContrasena(passwordEncoder.encode(pa.getUsuario().getContrasena()));
            usuario.setNombre(pa.getUsuario().getNombre());
            usuario.setNombreUsua(paciente.getDni());
            usuario.setApellido(pa.getUsuario().getApellido());
            usuario.setNumero(pa.getUsuario().getNumero());
            usuario.setSexo(pa.getUsuario().getSexo());
            usuario.setRol(rol);
            usuario.setEstado(estado);
            usuario = usuarioRepository.save(usuario);
    
            paciente.setUsuario(usuario);
            pacienteRepository.save(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body("Se registró con exito");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente ya existe en el sistema");
        }
    }
    
    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.isEmpty() || paciente.get().getUsuario().getEstado().getId() == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado");
        }
        Estado estado = estadoRepository.findById(2); // 1=Activo, 2=Inactivo
        Paciente recep = paciente.orElseThrow();
        recep.getUsuario().setEstado(estado);
        pacienteRepository.save(recep);
        return ResponseEntity.status(HttpStatus.OK).body("Paciente eliminado con exito");
    }

    @Override
    public boolean existsByUsuarioId(Long id) {
        return pacienteRepository.existsByUsuarioId(id);
    }

}
