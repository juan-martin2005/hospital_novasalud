package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.RecepcionistaDto;
import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEstadoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRecepcionistaRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.Validaciones;

@Service
public class RecepcionistaService implements IRecepcionistaService {

    @Autowired
    private IRecepcionistaRepository recepcionistaRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IRolRepository rolRepository;
    @Autowired
    private IEstadoRepository estadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<RecepcionistaDto> findAll() {
        return recepcionistaRepository.findByUsuario_EstadoId(1).stream().map(RecepcionistaDto::new).toList();
    }

    @Override
    public RecepcionistaDto findById(Long id) {
        return recepcionistaRepository.findById(id).map(RecepcionistaDto::new).orElse(null);
    }

    @Override
    public Validaciones save(Recepcionista re) {
        boolean existe = usuarioRepository.existsByNombreUsua(re.getUsuario().getNombreUsua());
        Rol rol = rolRepository.findByNombreRol("ROL_RECEPCIONISTA");
        Estado estadoActivo = estadoRepository.findById(1).orElseThrow();
        Recepcionista recepcionista = new Recepcionista();
        Usuario usuario = new Usuario();

        if (existe) {
            return Validaciones.YA_EXISTE;
        } 
        usuario.setNombreUsua(re.getUsuario().getNombreUsua());
        usuario.setContrasena(passwordEncoder.encode(re.getUsuario().getContrasena()));
        usuario.setNombre(re.getUsuario().getNombre());
        usuario.setApellido(re.getUsuario().getApellido());
        usuario.setNumero(re.getUsuario().getNumero());
        usuario.setSexo(re.getUsuario().getSexo());
        usuario.setEstado(estadoActivo);
        usuario.setRol(rol);
        usuario = usuarioRepository.save(usuario);

        recepcionista.setUsuario(usuario);
        recepcionistaRepository.save(recepcionista);
        return Validaciones.OK;
    }
    
    @Override
    public Validaciones deleteById(Long id) {
        Optional<Recepcionista> recepcionista = recepcionistaRepository.findById(id);
        if (recepcionista.isEmpty() || recepcionista.get().getUsuario().getEstado().getId() == 2) {
            return Validaciones.USUARIO_NO_ENCONTRADO;
        }
        Estado estado = estadoRepository.findById(2).orElseThrow(); // 1=Activo, 2=Inactivo
        Recepcionista recep = recepcionista.orElseThrow();
        recep.getUsuario().setEstado(estado);
        recepcionistaRepository.save(recep);
        return Validaciones.OK;
    }
    
    @Override
    public boolean existsByUsuarioId(Long id) {
        return recepcionistaRepository.existsByUsuarioId(id);
    }

    @Override
    public Optional<Recepcionista> findRecepcionistaById(Long id) {
        return recepcionistaRepository.findById(id);
    }
    
}
