package com.hospital_novasalud.hospital_nova_salud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicamentoDto (String id,
                              @NotBlank String nombre,
                              @NotBlank String descripcion,
                              @NotNull @Min(1) Integer cantidad, //Faltaba el Min(1)
                              @NotBlank String fecha_ingreso,
                              @NotBlank String fecha_vencimiento,
                              @NotNull Double precio_unitario){
}
