package com.hospital_novasalud.hospital_nova_salud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicamentoDto (String id,
                              @NotBlank String nombre,
                              @NotBlank String descripcion,
                              @NotNull Integer cantidad,
                              @NotBlank String fecha_ingreso,
                              @NotBlank String fecha_vencimiento,
                              @NotBlank String precio_unitario){
}
