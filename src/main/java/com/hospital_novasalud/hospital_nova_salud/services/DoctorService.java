package com.hospital_novasalud.hospital_nova_salud.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.dto.CitaMedicaEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.DoctorDto;
import com.hospital_novasalud.hospital_nova_salud.dto.DoctorEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.dto.GestionCitaDto;
import com.hospital_novasalud.hospital_nova_salud.dto.HorarioDoctorDto;
import com.hospital_novasalud.hospital_nova_salud.dto.HorarioDoctorEnvioDto;
import com.hospital_novasalud.hospital_nova_salud.models.CitaMedica;
import com.hospital_novasalud.hospital_nova_salud.models.Doctor;
import com.hospital_novasalud.hospital_nova_salud.models.Especialidad;
import com.hospital_novasalud.hospital_nova_salud.models.Estado;
import com.hospital_novasalud.hospital_nova_salud.models.EstadoCitaEnum;
import com.hospital_novasalud.hospital_nova_salud.models.HorarioDoctor;
import com.hospital_novasalud.hospital_nova_salud.models.Medicamento;
import com.hospital_novasalud.hospital_nova_salud.models.RecetaMedica;
import com.hospital_novasalud.hospital_nova_salud.models.Rol;
import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.ICitaMedicaRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEspecialidadRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IEstadoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IHorarioDoctorRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IMedicamentoRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRecetaMedicaRespository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IRolRepository;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;
import com.hospital_novasalud.hospital_nova_salud.validaciones.Validaciones;
import com.hospital_novasalud.hospital_nova_salud.validaciones.ValidarHorario;

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
    private ICitaMedicaRepository citaMedicaRepository;
    @Autowired
    private IRecetaMedicaRespository recetaMedicaRespository;
    @Autowired
    private IMedicamentoRepository medicamentoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<DoctorEnvioDto> findAll() {
        return doctorRepository.findByUsuario_EstadoId(1).stream().map(DoctorEnvioDto::new).toList();
    }
    @Override
    public List<CitaMedicaEnvioDto> findCitasByDoctor() {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        Doctor doctor = doctorRepository.findByUsuarioId(usuario.getId());


        return citaMedicaRepository.findByDoctor_IdAndEstado(doctor.getId(), EstadoCitaEnum.OCUPADO).stream().map(CitaMedicaEnvioDto::new).toList();
    }

    @Override
    public List<HorarioDoctorEnvioDto> findHorarioDoctor() {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName()).orElseThrow();
        Doctor doctor = doctorRepository.findByUsuarioId(usuario.getId());
        return horarioDoctorRepository.findByDoctor_Id(doctor.getId()).stream().map(HorarioDoctorEnvioDto::new).toList();
    }
    @Override
    public Validaciones save(DoctorDto doc) {
        boolean existeNombreUsuario = usuarioRepository.existsByNombreUsua(doc.getUsername());
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
        usuario.setNombreUsua(doc.getUsername());
        usuario.setContrasena(passwordEncoder.encode(doc.getContrasena()));
        usuario.setNombre(doc.getNombre());
        usuario.setApellido(doc.getApellido());
        usuario.setNumero(doc.getNumero());
        usuario.setSexo(doc.getSexo());
        usuario.setEstado(estadoActivo);
        usuario.setRol(rol);
        usuario = usuarioRepository.save(usuario);

        doctor.setUsuario(usuario);
        doctor.setEspecialidad(especialidad);
        doctorRepository.save(doctor);
        return Validaciones.OK;
    }

    @Override
    public ValidarHorario registrarHorario(HorarioDoctorDto horarioDoctor) {
        Authentication docActual = SecurityContextHolder.getContext().getAuthentication(); 
        Usuario usuario = usuarioRepository.findByNombreUsua(docActual.getName()).orElseThrow();
        Doctor doctor = doctorRepository.findByUsuarioId(usuario.getId());
        HorarioDoctor horario = new HorarioDoctor();

        boolean existeHorarioInicio = horarioDoctorRepository.existsByHorarioInicio(horarioDoctor.getHoraInicio());
        boolean existeHorarioFin = horarioDoctorRepository.existsByHorarioFin(horarioDoctor.getHoraFin());
        if(horarioDoctor.getFecha().isBefore(LocalDate.now())) {
            return ValidarHorario.FECHA_INVALIDA; //No se puede asignar un horario en una fecha pasada
        }
        if(existeHorarioInicio || existeHorarioFin) {
            return ValidarHorario.HORARIO_EN_USO; //El horario ya está en uso
        }
        horario.setDoctor(doctor);
        horario.setFecha(horarioDoctor.getFecha());
        horario.setHorarioInicio(horarioDoctor.getHoraInicio());
        horario.setHorarioFin(horarioDoctor.getHoraFin());
        horarioDoctorRepository.save(horario);
        return ValidarHorario.OK; //Horario registrado con éxito
    }
    @Override
    public GestionCitaDto finalizarCita(GestionCitaDto gestionCita, Long id) {
        Authentication usuarioName = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsua(usuarioName.getName())
                .orElseThrow();
        Doctor doctor = doctorRepository.findByUsuarioId(usuario.getId());


        CitaMedica cita = citaMedicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        //DOCTOR Y CITA DEBEN ESTAR RELACIONADOS
        if (!cita.getDoctor().getId().equals(doctor.getId())) {
            throw new RuntimeException("No puedes modificar citas de otro doctor");
        }

        String descripcion = gestionCita.descripcionReceta();
        List<Long> medicamento = gestionCita.medicamentosReceta();

        RecetaMedica nuevaReceta = new RecetaMedica();
        nuevaReceta.setFechaActual(cita.getHorarioDoctor().getFecha().atStartOfDay());
        nuevaReceta.setDescripcion(descripcion);
        nuevaReceta.setCita(cita);

        List<Medicamento> medicamentos = new ArrayList<>();
        for (Long medicamentoId : medicamento){
            Medicamento m = medicamentoRepository.findById(medicamentoId).
                    orElseThrow(()-> new IllegalArgumentException("Medicamento no encontrado"));
            medicamentos.add(m);
        }
        nuevaReceta.setMedicamentos(medicamentos);

        recetaMedicaRespository.save(nuevaReceta);

        cita.setEstado(EstadoCitaEnum.FINALIZADO);
        citaMedicaRepository.save(cita);

        return new GestionCitaDto("Finalizado",descripcion, medicamento);

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
    public Especialidad findEspecialidad(DoctorDto doc){
        return especialidadRepository.findById(doc.getEspecialidad()).orElse(null);
    }

    @Override
    public Optional<Doctor> findDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

}
