package com.hospital_novasalud.hospital_nova_salud.services;

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
import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;
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
    public List<CitaMedicaEnvioDto> findByPaciente() {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        Paciente paciente = pacienteRepository.findByUsuarioId(usuario.getId());
        return citaMedicaRepository.findByPaciente_Id(paciente.getId()).stream().map(CitaMedicaEnvioDto::new).toList();
    }

    @Override
    public ValidarHorario save(CitaMedicaDto cita) {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        Optional<Doctor> doctor = doctorRepository.findById(cita.getDoctorId());
        Paciente paciente = pacienteRepository.findByUsuarioId(usuario.getId());
        Optional<HorarioDoctor> horario = horarioDoctorRepository.findById(cita.getHoraCita());

        boolean exiteFechayHora = citaMedicaRepository.existsByHorarioDoctor_Id(cita.getHoraCita());

        CitaMedica citaMedica = new CitaMedica();
        //Verificar si la hora de la cita médica concuerda con el hoario del doctor
        if(doctor.isEmpty()){
            return ValidarHorario.ERROR;
        }
        if(horario.isEmpty()){
            return ValidarHorario.HORARIO_NO_DISPONIBLE;
        }
        if(exiteFechayHora){
            return ValidarHorario.HORARIO_EN_USO;
        }
        citaMedica.setPaciente(paciente);
        citaMedica.setDoctor(doctor.orElseThrow());
        citaMedica.setHorarioDoctor(horario.orElseThrow());
        citaMedicaRepository.save(citaMedica);
        return ValidarHorario.OK;
    }

    @Override
    public void deleteById(Long id) {
    }

}
