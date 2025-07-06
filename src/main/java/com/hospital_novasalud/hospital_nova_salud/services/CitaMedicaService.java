package com.hospital_novasalud.hospital_nova_salud.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.BoletaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.models.Boleta;
import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.EstadoCitaEnum;
import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;
import com.hospital_novasalud.hospital_nova_salud.models.Paciente;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IBoletaRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.ICitaMedicaRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IHorarioDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IPacienteRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

import jakarta.transaction.Transactional;

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
    @Autowired
    private IBoletaRepository boletaRepository;

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

        if(doctor.isEmpty()){
            return ValidarHorario.ERROR;
        }
        if(horario.isEmpty()){
            return ValidarHorario.HORARIO_NO_DISPONIBLE;
        }
        if(exiteFechayHora){
            return ValidarHorario.HORARIO_EN_USO;
        }
        CitaMedica citaMedica = new CitaMedica();
        citaMedica.setPaciente(paciente);
        citaMedica.setDoctor(doctor.orElseThrow());
        citaMedica.setHorarioDoctor(horario.orElseThrow());
        citaMedicaRepository.save(citaMedica);

        Boleta boleta = new Boleta();
        boleta.setPaciente(paciente);
        boleta.setDoctor(doctor.orElseThrow());
        boleta.setFecha(LocalDateTime.now());
        boleta.setMonto(calcularMontoConsulta(doctor.get())); // Ajusta este método si quieres personalizar
        boletaRepository.save(boleta);
        return ValidarHorario.OK;
    }
    private double calcularMontoConsulta(Doctor doctor) {
        return 50.0;
}
    @Override
    public void deleteById(Long id) {
    }

    @Override
    @Transactional
    public BoletaDto generarBoletaParaCita(Long citaId) {
        CitaMedica cita = citaMedicaRepository.findById(citaId)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + citaId));

        Boleta boleta = new Boleta();
        boleta.setPaciente(cita.getPaciente());
        boleta.setDoctor(cita.getDoctor());
        boleta.setFecha(LocalDateTime.now());
        boleta.setMonto(50.0);

        boleta = boletaRepository.save(boleta);

        return new BoletaDto(boleta);
}
    @Override
    @Transactional
    public CitaMedicaEnvioDto saveAndReturnDto(CitaMedicaDto cita) {
    Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
    Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

    Optional<Doctor> doctorOpt = doctorRepository.findById(cita.getDoctorId());
    if (doctorOpt.isEmpty()) {
        throw new RuntimeException("El doctor especificado no existe.");
    }

    Optional<HorarioDoctor> horarioOpt = horarioDoctorRepository.findById(cita.getHoraCita());
    if (horarioOpt.isEmpty()) {
        throw new RuntimeException("El horario especificado no existe.");
    }

    boolean existeFechayHora = citaMedicaRepository.existsByHorarioDoctor_Id(cita.getHoraCita());
    if (existeFechayHora) {
        throw new RuntimeException("El horario ya está ocupado. Elija otro.");
    }

    Paciente paciente = pacienteRepository.findByUsuarioId(usuario.getId());
    Doctor doctor = doctorOpt.get();
    HorarioDoctor horario = horarioOpt.get();

    CitaMedica nuevaCita = new CitaMedica();
    nuevaCita.setPaciente(paciente);
    nuevaCita.setDoctor(doctor);
    nuevaCita.setHorarioDoctor(horario);
    nuevaCita.setEstado(EstadoCitaEnum.OCUPADO); // o el estado por defecto que uses
    // Guardar
    nuevaCita = citaMedicaRepository.save(nuevaCita);
    // Retornar el DTO completo con ID
    return new CitaMedicaEnvioDto(nuevaCita);
}
}
