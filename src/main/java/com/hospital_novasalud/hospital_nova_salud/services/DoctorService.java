package com.hospital_novasalud.hospital_nova_salud.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEspecialidadRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEstadoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IHorarioDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.ValidacionHorario;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.Validaciones;

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
    private IHorarioDoctorRepository horarioDoctorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<DoctorDto> findAll() {
        return doctorRepository.findByUsuario_EstadoId(1).stream().map(DoctorDto::new).toList();
    }
    @Override
    public Validaciones save(Doctor doc) {
        boolean existeNombreUsuario = usuarioRepository.existsByNombreUsua(doc.getUsuario().getNombreUsua());
        Especialidad especialidad = findEspecialidad(doc);
        Rol rol = rolRepository.findByNombreRol("ROL_DOCTOR");
        Estado estadoActivo = estadoRepository.findById(1).orElseThrow();
        Doctor doctor = new Doctor();
        Usuario usuario = new Usuario();
        if(especialidad == null){
            return Validaciones.ESPECIALIDAD_NO_ENCONTRADA;
        }
        if(existeNombreUsuario){
            return Validaciones.YA_EXISTE;
        }
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
        doctorRepository.save(doctor);
        return Validaciones.OK;
    }

    @Override
    public ValidacionHorario updateHorario(HorarioDoctor horarioDoctor) {
        Authentication docActual = SecurityContextHolder.getContext().getAuthentication(); 
        Usuario usuario = usuarioRepository.findByNombreUsua(docActual.getName()).orElseThrow();
        Doctor doctor = doctorRepository.findByUsuarioId(usuario.getId());

        boolean existeHorarioInicio = horarioDoctorRepository.existsByHorarioInicio(horarioDoctor.getHorarioInicio());
        boolean existeHorarioFin = horarioDoctorRepository.existsByHorarioFin(horarioDoctor.getHorarioFin());
        if(horarioDoctor.getDia().isBefore(LocalDate.now())) {
            return ValidacionHorario.FECHA_INVALIDA; //No se puede asignar un horario en una fecha pasada
        }
        if(horarioDoctor.getHorarioInicio().isBefore(LocalTime.now()) ||
           horarioDoctor.getHorarioFin().isBefore(LocalTime.now())) {
            return ValidacionHorario.HORARIO_INVALIDO; //El horario ingresado es invalido 
        }
        if(existeHorarioInicio || existeHorarioFin) {
            return ValidacionHorario.HORARIO_EN_USO; //El horario ya está en uso
        }
        horarioDoctor.setDoctor(doctor);
        horarioDoctor.setDia(horarioDoctor.getDia());
        horarioDoctor.setHorarioInicio(horarioDoctor.getHorarioInicio());
        horarioDoctor.setHorarioFin(horarioDoctor.getHorarioFin());
        horarioDoctorRepository.save(horarioDoctor);
        return ValidacionHorario.OK; //Horario registrado con éxito
    }

    @Override
    public Validaciones deleteById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isEmpty() || doctor.get().getUsuario().getEstado().getId() == 2){
            return Validaciones.USUARIO_NO_ENCONTRADO;
        }
        Estado estado = estadoRepository.findById(2).orElseThrow(); //1=Activo, 2=Inactivo
        Doctor doc = doctor.orElseThrow();
        doc.getUsuario().setEstado(estado);
        doctorRepository.save(doc);
        return Validaciones.OK;
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
