package com.example.dto;

import com.example.models.Pelicula;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyDTO {

    private Pelicula peli;
    private String copiaEstado;
    private String copiaSoporte;
}
