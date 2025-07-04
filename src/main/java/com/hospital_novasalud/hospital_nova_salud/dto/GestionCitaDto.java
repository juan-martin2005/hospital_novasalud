package com.hospital_novasalud.hospital_nova_salud.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GestionCitaDto(String estado,
                              @NotBlank String descripcionReceta,
                              @NotNull List<Long> medicamentosReceta) {

}
