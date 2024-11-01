package com.example.models.dto;

import com.example.models.Copia;
import com.example.models.Pelicula;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyDTO {

    private String tituloPeli;
    private String estadoCopia;
    private String soporteCopia;

}
