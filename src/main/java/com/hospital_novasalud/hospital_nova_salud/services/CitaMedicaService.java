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
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.ICitaMedicaRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IHorarioDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IPacienteRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

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
    public List<CitaMedicaEnvioDto> findAll() {
        return citaMedicaRepository.findAll().stream().map(CitaMedicaEnvioDto::new).toList();
    }

    @Override
    public ValidarHorario save(CitaMedicaDto cita) {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        Optional<Doctor> doctor = doctorRepository.findById(cita.getDoctor());
        Paciente paciente = pacienteRepository.findByUsuarioId(usuario.getId());
        boolean existeFechaDoctor = horarioDoctorRepository.existsByDia(cita.getFechaCita());
        boolean existeHorarioDoctor = horarioDoctorRepository.existsByHorarioInicio(cita.getHoraCita());
        boolean citaMedicaEnUso = citaMedicaRepository.existsByDoctorAndFechaCitaAndHoraCita(doctor.orElseThrow(), cita.getFechaCita(), cita.getHoraCita());
        CitaMedica citaMedica = new CitaMedica();
        //Verificar si la hora de la cita médica concuerda con el hoario del doctor
        if(doctor.isEmpty()){
            return ValidarHorario.ERROR;
        }
        if(cita.getFechaCita().isBefore(LocalDate.now())) {
            return ValidarHorario.FECHA_INVALIDA; //No se puede ingresar una fecha pasada
        }
        if(cita.getHoraCita().isBefore(LocalTime.now())){
            return ValidarHorario.HORARIO_INVALIDO; //El horario ingresado es invalido 
        }
        if(!existeFechaDoctor || !existeHorarioDoctor){ //NOTA: Falta validar que la hora sea independiente de cada doctor
            return ValidarHorario.HORARIO_NO_DISPONIBLE; //El doctor no tiene horario disponible
        }
        if(citaMedicaEnUso){
            return ValidarHorario.HORARIO_EN_USO; //El horario ya esta en uso
        }
        citaMedica.setPaciente(paciente);
        citaMedica.setDoctor(doctor.orElseThrow());
        citaMedica.setFechaCita(cita.getFechaCita());
        citaMedica.setHoraCita(cita.getHoraCita());
        citaMedicaRepository.save(citaMedica);
        return ValidarHorario.OK;
    }

    @Override
    public void deleteById(Long id) {
    }

}
