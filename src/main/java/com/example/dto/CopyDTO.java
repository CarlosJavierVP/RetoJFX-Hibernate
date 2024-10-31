package com.example.dto;

import com.example.models.Copia;
import com.example.models.Pelicula;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyDTO {

    private Pelicula peli;
    private Copia copia;

    public String getTituloPeli() {
        return peli.getTitulo();
    }
    public String getEstadoCopia(){
        return copia.getEstado();
    }
    public String getSoporteCopia(){
        return copia.getSoporte();
    }

}
