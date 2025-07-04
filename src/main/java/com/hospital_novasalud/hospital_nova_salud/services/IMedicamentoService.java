package com.hospital_novasalud.hospital_nova_salud.services;

import com.hospital_novasalud.hospital_nova_salud.dto.MedicamentoDto;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IMedicamentoService{
    MedicamentoDto findById(@PathVariable Long id);
    List<MedicamentoDto> findAll();
    MedicamentoDto addMedicamento(MedicamentoDto medicamento);
    MedicamentoDto updateMedicamento(MedicamentoDto medicamento, Long id);
    String deleteMedicamento(@PathVariable Long id);
}
