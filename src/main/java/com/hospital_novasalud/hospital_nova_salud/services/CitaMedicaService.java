package com.hospital_novasalud.hospital_nova_salud.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.ICitaMedicaRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IHorarioDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IPacienteRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;
import com.hospital_novasalud.hospital_nova_salud.resultEnum.ValidacionHorario;

@Service
public class CitaMedicaService implements ICitaMedicaService{
    
    @Autowired
    private ICitaMedicaRepository citaMedicaRepository;
    @Autowired 
    private IDoctorRepository doctorRepository;
    @Autowired
    private IPacienteRepository pacienteRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IHorarioDoctorRepository horarioDoctorRepository;
    @Override
    public List<CitaMedicaDto> findAll() {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        System.out.println(usuarioName.getName().toString());
        System.out.println(usuario.getId());
        return citaMedicaRepository.findAll().stream().map(CitaMedicaDto::new).toList();
    }

    @Override
    public ValidacionHorario save(CitaMedica citaMedica) {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        Optional<Doctor> doctor = doctorRepository.findById(citaMedica.getDoctor().getId());
        Paciente paciente = pacienteRepository.findByUsuarioId(usuario.getId());
        boolean existeFechaDoctor = horarioDoctorRepository.existsByDia(citaMedica.getFechaCita());
        boolean existeHorarioDoctor = horarioDoctorRepository.existsByHorarioInicio(citaMedica.getHoraCita());
        boolean citaMedicaEnUso = citaMedicaRepository.existsByDoctorAndFechaCitaAndHoraCita(doctor.orElseThrow(), citaMedica.getFechaCita(), citaMedica.getHoraCita());
        //Verificar si la hora de la cita médica concuerda con el hoario del doctor
        if(doctor.isEmpty()){
            return ValidacionHorario.ERROR;
        }
        if(citaMedica.getFechaCita().isBefore(LocalDate.now())) {
            return ValidacionHorario.FECHA_INVALIDA; //No se puede ingresar una fecha pasada
        }
        if(citaMedica.getHoraCita().isBefore(LocalTime.now())){
            return ValidacionHorario.HORARIO_INVALIDO; //El horario ingresado es invalido 
        }
        if(!existeFechaDoctor || !existeHorarioDoctor){ //NOTA: Falta validar que la hora sea independiente de cada doctor
            return ValidacionHorario.HORARIO_NO_DISPONIBLE; //El doctor no tiene horario disponible
        }
        if(citaMedicaEnUso){
            return ValidacionHorario.HORARIO_EN_USO; //El horario ya esta en uso
        }
        citaMedica.setPaciente(paciente);
        citaMedica.setDoctor(doctor.orElseThrow());
        citaMedica.setFechaCita(citaMedica.getFechaCita());
        citaMedica.setHoraCita(citaMedica.getHoraCita());
        citaMedicaRepository.save(citaMedica);
        return ValidacionHorario.OK;
    }

    @Override
    public void deleteById(Long id) {
    }

}
