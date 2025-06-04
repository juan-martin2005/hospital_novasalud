package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private IDoctorRepository doctorRepository;
    @Autowired
    private IEspecialidadRepository especialidadRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IRolRepository rolRepository;
    @Autowired
    private IEstadoRepository estadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<DoctorDto> findAll() {
        return doctorRepository.findByUsuario_EstadoId(1).stream().map(DoctorDto::new).toList();
    }
    @Override
    public boolean save(Doctor doc) {
        boolean existe = usuarioRepository.existsByNombreUsua(doc.getUsuario().getNombreUsua());
        Especialidad especialidad = findEspecialidad(doc);
        Rol rol = rolRepository.findByNombreRol("ROL_DOCTOR");
        Estado estadoActivo = estadoRepository.findById(1).orElseThrow();
        Doctor doctor = new Doctor();
        Usuario usuario = new Usuario();

        if(!existe){
            usuario.setNombreUsua(doc.getUsuario().getNombreUsua());
            usuario.setContrasena(passwordEncoder.encode(doc.getUsuario().getContrasena()));
            usuario.setNombre(doc.getUsuario().getNombre());
            usuario.setApellido(doc.getUsuario().getApellido());
            usuario.setNumero(doc.getUsuario().getNumero());
            usuario.setSexo(doc.getUsuario().getSexo());
            usuario.setEstado(estadoActivo);
            usuario.setRol(rol);
            usuario = usuarioRepository.save(usuario);
    
            doctor.setUsuario(usuario);
            doctor.setEspecialidad(especialidad);
            doctor.setHorarioAtencion(doc.getHorarioAtencion());
            doctorRepository.save(doctor);
        }
        return existe;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        Estado estado = estadoRepository.findById(2).orElseThrow(); //1=Activo, 2=Inactivo
        Doctor doc = doctor.orElseThrow();
        doc.getUsuario().setEstado(estado);
        doctorRepository.save(doc);
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
    @Override
    public Especialidad findEspecialidad(Doctor doc){
        return especialidadRepository.findById(doc.getEspecialidad().getId()).orElse(null);
    }

    @Override
    public Optional<Doctor> findDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
}
