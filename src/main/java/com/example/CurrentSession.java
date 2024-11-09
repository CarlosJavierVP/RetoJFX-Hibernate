package com.example;

import com.example.models.Copia;
import com.example.models.Pelicula;
import com.example.models.dto.CopyDTO;
import com.example.models.Usuario;
import java.util.List;

public class CurrentSession {
    public static Usuario userSelected = null;
    public static List<Copia> listCopySelected = null;
    public static List<CopyDTO> listDTOselected = null;
    public static Pelicula movieSelected = null;
    public static CopyDTO copyDTOselected = null;
    public static Copia copySelected = null;



    public static void setParamsToNull(){
        userSelected = null;
        listCopySelected = null;
        listDTOselected = null;
        movieSelected = null;
        copyDTOselected = null;
        copySelected = null;
    }

}
