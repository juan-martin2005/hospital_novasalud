package com.hospital_novasalud.hospital_nova_salud.services;

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
import com.hospital_novasalud.hospital_nova_salud.repositories.IPacienteRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;

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

    @Override
    public List<CitaMedicaDto> findAll() {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        System.out.println(usuarioName.getName().toString());
        System.out.println(usuario.getId());
        return citaMedicaRepository.findAll().stream().map(CitaMedicaDto::new).toList();
    }

    @Override
    public boolean save(CitaMedica citaMedica) {
        boolean horaDisponible = doctorRepository.existsByHorarioAtencion(citaMedica.getHoraCita());
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        Optional<Doctor> doctor = doctorRepository.findById(citaMedica.getDoctor().getId());
        Paciente paciente = pacienteRepository.findByUsuarioId(usuario.getId());
        //Verificar si la hora de la cita médica concuerda con el hoario del doctor
        if(doctor.isEmpty()){
            return false;
        }
        if(horaDisponible){
            citaMedica.setPaciente(paciente);
            citaMedica.setDoctor(doctor.orElseThrow());
            citaMedica.setFechaCita(citaMedica.getFechaCita());
            citaMedica.setHoraCita(citaMedica.getHoraCita());
            citaMedicaRepository.save(citaMedica);
        }
        return horaDisponible;
    }

    @Override
    public void deleteById(Long id) {
    }

}
