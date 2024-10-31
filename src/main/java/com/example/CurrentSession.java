package com.example;

import com.example.dto.CopyDTO;
import com.example.models.Copia;
import com.example.models.Usuario;

import java.util.List;

public class CurrentSession {

    public static Usuario userSelected = null;
    public static CopyDTO copySelected = null;


    public static void setParamsToNull(){
        userSelected = null;
        copySelected = null;
    }

}
