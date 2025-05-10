package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEspecialidadRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

@Service
public class DoctorService implements IDoctorService{

    @Autowired
    IDoctorRepository doctorRepository;
    @Autowired
    IEspecialidadRepository especialidadRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;

    @Override
    public List<DoctorDto> findAll() {
        return doctorRepository.findAll().stream().map(DoctorDto::new).toList();
    }

    @Override
    public ResponseEntity<?> save(Doctor doc) {
        
        Especialidad especialidad = especialidadRepository.findById(doc.getEspecialidad().getId()).orElse(null);
        Usuario usuario = usuarioRepository.findById(doc.getUsuario().getId()).orElse(null);

        if(especialidad == null || usuario == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la especialidad o el usuario");
        }

        if(doc.getId() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un doctor");
        }
        if(existsByUsuarioId(doc.getUsuario().getId()) && existsByEspecialidadId(doc.getEspecialidad().getId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El doctor ya cuenta con esa especialidad");
        }
        Doctor doctor = new Doctor();
        doctor.setUsuario(usuario);
        doctor.setEspecialidad(especialidad);
        doctor.setHorarioAtencion(doc.getHorarioAtencion());
        doctorRepository.save(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se registro el doctor con exito");
        
        
    }

    @Override
    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public boolean existsByEspecialidadId(Long id) {    
        return doctorRepository.existsByEspecialidadId(id);
    }

    @Override
    public boolean existsByUsuarioId(Long id) {
        return doctorRepository.existsByUsuarioId(id);
    }

}
