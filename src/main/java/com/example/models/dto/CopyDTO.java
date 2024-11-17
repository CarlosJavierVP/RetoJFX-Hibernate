package com.example.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase CopyDTO para transportar datos
 * @author Carlos Javier
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyDTO {

    private String tituloPeli;
    private String estadoCopia;
    private String soporteCopia;
    private Long idMyCopy;
    private Long idMyMovie;


}
