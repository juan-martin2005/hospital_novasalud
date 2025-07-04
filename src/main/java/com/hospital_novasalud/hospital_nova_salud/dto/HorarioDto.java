package com.hospital_novasalud.hospital_nova_salud.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({"id","fecha","horaInicio", "horaFin"})
public record HorarioDto(String id,
                         @NotBlank String fecha,
                         @NotBlank String horaInicio,
                         @NotBlank String horaFin) {

}
