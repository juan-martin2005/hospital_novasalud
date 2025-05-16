package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEspecialidadRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEstadoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

@Service
public class DoctorService implements IDoctorService{

    @Autowired
    IDoctorRepository doctorRepository;
    @Autowired
    IEspecialidadRepository especialidadRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IRolRepository rolRepository;

    @Autowired
    IEstadoRepository estadoRepository;
    @Override
    public List<DoctorDto> findAll() {
        return doctorRepository.findByUsuario_EstadoId(1).stream().map(DoctorDto::new).toList();
    }

    @Override
    public ResponseEntity<?> save(Doctor doc) {
        Especialidad especialidad = especialidadRepository.findById(doc.getEspecialidad().getId()).orElse(null);
        Rol rol = rolRepository.findById(2);
        Estado estado = estadoRepository.findById(1); //1=Activo, 2=Inactivo       
        Doctor doctor = new Doctor();
        Usuario usuario = new Usuario();

        if(especialidad == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la especialidad");
        }
        if(!usuarioRepository.existsByNombreUsua(doc.getUsuario().getNombreUsua()) ){
            
            usuario.setNombreUsua(doc.getUsuario().getNombreUsua());
            usuario.setContrasena(doc.getUsuario().getContrasena());
            usuario.setNombre(doc.getUsuario().getNombre());
            usuario.setApellido(doc.getUsuario().getApellido());
            usuario.setDni(doc.getUsuario().getDni());
            usuario.setNumero(doc.getUsuario().getNumero());
            usuario.setSexo(doc.getUsuario().getSexo());
            usuario.setRol(rol);
            usuario.setEstado(estado);
            usuario = usuarioRepository.save(usuario);
    
            doctor.setUsuario(usuario);
            doctor.setEspecialidad(especialidad);
            doctor.setHorarioAtencion(doc.getHorarioAtencion());
            doctorRepository.save(doctor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Se registro al doctor con exito");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario para este doctor ya existe");
        }
    
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        
        if(doctor.isEmpty() || doctor.get().getUsuario().getEstado().getId() == 2){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor no encontrado");
        }
        Estado estado = estadoRepository.findById(2); //1=Activo, 2=Inactivo
        Doctor doc = doctor.orElseThrow();
        doc.getUsuario().setEstado(estado);
        doctorRepository.save(doc);
        return ResponseEntity.status(HttpStatus.OK).body("Doctor eliminado con exito");
    }

    @Override
    public boolean existsByEspecialidadId(Long id) {    
        return doctorRepository.existsByEspecialidadId(id);
    }

    @Override
    public boolean existsByUsuarioId(Long id) {
        return doctorRepository.existsByUsuarioId(id);
    }

    @Override
    public List<DoctorDto> findByEspecialidad(Long id) {  
        return doctorRepository.findByEspecialidadId(id).stream().map(DoctorDto::new).toList();
    }

}
