package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@Service
public class RecepcionistaService implements IRecepcionistaService {

    @Autowired
    IRecepcionistaRepository recepcionistaRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IRolRepository rolRepository;
    @Autowired
    IEstadoRepository estadoRepository;

    @Override
    public List<RecepcionistaDto> findAll() {
        return recepcionistaRepository.findByUsuario_EstadoId(1).stream().map(RecepcionistaDto::new).toList();
    }

    @Override
    public RecepcionistaDto findById(Long id) {
        return recepcionistaRepository.findById(id).map(RecepcionistaDto::new).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Recepcionista re) {
        Rol rol = rolRepository.findById(3);
        Recepcionista recepcionista = new Recepcionista();
        Usuario usuario = new Usuario();
        Estado estado = estadoRepository.findById(1);

        if (!usuarioRepository.existsByNombreUsua(re.getUsuario().getNombreUsua())) {

            usuario.setNombreUsua(re.getUsuario().getNombreUsua());
            usuario.setContrasena(re.getUsuario().getContrasena());
            usuario.setNombre(re.getUsuario().getNombre());
            usuario.setApellido(re.getUsuario().getApellido());
            usuario.setDni(re.getUsuario().getDni());
            usuario.setNumero(re.getUsuario().getNumero());
            usuario.setSexo(re.getUsuario().getSexo());
            usuario.setRol(rol);
            usuario.setEstado(estado);
            usuario = usuarioRepository.save(usuario);

            recepcionista.setUsuario(usuario);
            recepcionistaRepository.save(recepcionista);
            return ResponseEntity.status(HttpStatus.CREATED).body("Se registro al recepcionista con exito");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario para este recepcionista ya existe");
        }

    }

    @Override
    public boolean existsByUsuarioId(Long id) {
        return recepcionistaRepository.existsByUsuarioId(id);
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Optional<Recepcionista> recepcionista = recepcionistaRepository.findById(id);

        if (recepcionista.isEmpty() || recepcionista.get().getUsuario().getEstado().getId() == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recepcionista no encontrado");
        }
        Estado estado = estadoRepository.findById(2); // 1=Activo, 2=Inactivo
        Recepcionista recep = recepcionista.orElseThrow();
        recep.getUsuario().setEstado(estado);
        recepcionistaRepository.save(recep);
        return ResponseEntity.status(HttpStatus.OK).body("Recepcionista eliminado con exito");
    }

}
