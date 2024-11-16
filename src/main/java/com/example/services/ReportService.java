package com.example.services;

import net.sf.jasperreports.engine.*;
import java.sql.Connection;
import java.util.HashMap;

public class ReportService {
    private static Connection con;
    public ReportService(Connection conect) {
        con = conect;
    }

    public void generarInformePeliculas(){
        try{
            JasperPrint jp = JasperFillManager.fillReport("listadoPelis.jasper", null, con);
            JasperExportManager.exportReportToPdfFile(jp,"AllMovies.pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void generarInformeCopias(){
        try{
            JasperPrint jp = JasperFillManager.fillReport("listadoCopias.jasper", null, con);
            JasperExportManager.exportReportToPdfFile(jp,"MyCopies.pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
    public void informeCopiasSoporteEstado(String soporte, String estado){
        var params = new HashMap<>();
        params.put("Soporte", soporte);
        params.put("Estado", estado);

        try{
            JasperPrint jp = JasperFillManager.fillReport("copiasSoporteEstado.jasper", params, con);
            JasperExportManager.exportReportToPdfFile(jp,"MyCopies"+soporte+estado+".pdf");
        }catch (JRException e){
            throw new RuntimeException();
        }

    }

}
