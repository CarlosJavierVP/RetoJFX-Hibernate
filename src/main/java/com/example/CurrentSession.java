package com.example;

import com.example.dto.CopyDTO;
import com.example.models.Usuario;

public class CurrentSession {

    public static Usuario userSelected = null;
    public static CopyDTO copySelected = null;


    public static void setParamsToNull(){
        userSelected = null;
        copySelected = null;
    }

}
