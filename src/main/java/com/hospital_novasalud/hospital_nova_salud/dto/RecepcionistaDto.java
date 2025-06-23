package com.hospital_novasalud.hospital_nova_salud.dto;

import com.hospital_novasalud.hospital_nova_salud.models.Recepcionista;

public class RecepcionistaDto extends UsuarioDto{
    
    public RecepcionistaDto(){
    }
    public RecepcionistaDto(Recepcionista re){
        super(re.getUsuario());
    }
}
