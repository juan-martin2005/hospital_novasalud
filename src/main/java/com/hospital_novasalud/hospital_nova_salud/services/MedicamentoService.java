package com.hospital_novasalud.hospital_nova_salud.services;

import com.hospital_novasalud.hospital_nova_salud.dto.MedicamentoDto;
import com.hospital_novasalud.hospital_nova_salud.models.Medicamento;
import com.hospital_novasalud.hospital_nova_salud.repositories.IMedicamentoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicamentoService implements IMedicamentoService {
    @Autowired
    IMedicamentoRepository medicamentoRepository;

    @Override
    public MedicamentoDto findById(Long id) {
        Optional<Medicamento> medicamentoEntity = this.medicamentoRepository.findById(id);

        if(medicamentoEntity.isPresent()) {
            Medicamento medicamento = medicamentoEntity.get();

            return new MedicamentoDto(medicamento.getId().toString(),
                    medicamento.getNombre(),
                    medicamento.getDescripcion(),
                    medicamento.getCantidad(),
                    medicamento.getFechaIngreso().toString(),
                    medicamento.getFechaVencimiento().toString(),
                    medicamento.getPrecioUnitario());
        }else{
            throw new EntityNotFoundException("Medicamento no encontrado");
        }
    }

    @Override
    public List<MedicamentoDto> findAll() {
        List<Medicamento> medicamentos = this.medicamentoRepository.findAll();

        return medicamentos.stream()
                .map(medicamento -> new MedicamentoDto(medicamento.getId().toString(),
                        medicamento.getNombre(),
                        medicamento.getDescripcion(),
                        medicamento.getCantidad(),
                        medicamento.getFechaIngreso().toString(),
                        medicamento.getFechaVencimiento().toString(),
                        medicamento.getPrecioUnitario()))
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoDto addMedicamento(MedicamentoDto medicamentoDTO) {
        String nombre = medicamentoDTO.nombre();
        String descripcion = medicamentoDTO.descripcion();
        int cantidad = medicamentoDTO.cantidad();
        String fechaIngreso = medicamentoDTO.fecha_ingreso().toString();
        String fechaVencimiento = medicamentoDTO.fecha_vencimiento().toString();
        String precioUnitario = String.valueOf(medicamentoDTO.precio_unitario());

        Optional<Medicamento> medicamento = this.medicamentoRepository.findByNombre(nombre);

        if(!medicamento.isPresent()) {
            Medicamento newMedicamento = new Medicamento();
            newMedicamento.setNombre(nombre);
            newMedicamento.setDescripcion(descripcion);
            newMedicamento.setCantidad(Integer.valueOf(cantidad));
            newMedicamento.setFechaIngreso(LocalDate.parse(fechaIngreso));
            newMedicamento.setFechaVencimiento(LocalDate.parse(fechaVencimiento));
            newMedicamento.setPrecioUnitario(Double.parseDouble(precioUnitario));

            Medicamento savedMedicamento = this.medicamentoRepository.save(newMedicamento);

            return new MedicamentoDto(savedMedicamento.getId().toString(),
                    savedMedicamento.getNombre(),
                    savedMedicamento.getDescripcion(),
                    savedMedicamento.getCantidad(),
                    savedMedicamento.getFechaIngreso().toString(),
                    savedMedicamento.getFechaVencimiento().toString(),
                    savedMedicamento.getPrecioUnitario());
        }else{
            throw new EntityNotFoundException("Medicamento "+ nombre +" ya existe");
        }
    }

    @Override
    public MedicamentoDto updateMedicamento(MedicamentoDto medicamentoDTO, Long id) {
        String nombre = medicamentoDTO.nombre();
        String descripcion = medicamentoDTO.descripcion();
        int cantidad = medicamentoDTO.cantidad();
        String fechaIngreso = medicamentoDTO.fecha_ingreso().toString();
        String fechaVencimiento = medicamentoDTO.fecha_vencimiento().toString();
        String precioUnitario = String.valueOf(medicamentoDTO.precio_unitario());

        Medicamento medicamentoEntity = this.medicamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El id del medicamento no existe"));

        Optional<Medicamento> medicamentoNombre = this.medicamentoRepository.findByNombre(nombre);

        if(medicamentoNombre.isPresent() && !medicamentoNombre.get().getId().equals(id)) {
            throw new EntityNotFoundException("El Medicamento con id: "+ id +" nombre: "+ nombre +" ya existe");
        }

        medicamentoEntity.setNombre(nombre);
        medicamentoEntity.setDescripcion(descripcion);
        medicamentoEntity.setCantidad(cantidad);
        medicamentoEntity.setFechaIngreso(LocalDate.parse(fechaIngreso));
        medicamentoEntity.setFechaVencimiento(LocalDate.parse(fechaVencimiento));
        medicamentoEntity.setPrecioUnitario(Double.valueOf(precioUnitario));

        Medicamento savedMedicamento = this.medicamentoRepository.saveAndFlush(medicamentoEntity);
        return new MedicamentoDto(savedMedicamento.getId().toString(),
                savedMedicamento.getNombre(),
                savedMedicamento.getDescripcion(),
                savedMedicamento.getCantidad(),
                savedMedicamento.getFechaIngreso().toString(),
                savedMedicamento.getFechaVencimiento().toString(),
                savedMedicamento.getPrecioUnitario());
    }

    @Override
    public String deleteMedicamento(Long id) {
        Optional<Medicamento> medicamentoEntity = this.medicamentoRepository.findById(id);

        if(medicamentoEntity.isPresent()) {
            this.medicamentoRepository.delete(medicamentoEntity.get());
            return "Medicamento: " + medicamentoEntity.get().getNombre() + " eliminado";
        }else{
            throw new EntityNotFoundException("Medicamento "+ id +" no existe");
        }
    }
}
