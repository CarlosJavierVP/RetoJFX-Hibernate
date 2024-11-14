package com.example.services;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import java.sql.Connection;

public class ReportService {
    private static Connection con;
    public ReportService(Connection conect) {
        con = conect;
    }

    public void generarInforme(){
        try{
            JasperPrint jp = JasperFillManager.fillReport("listadoPelis.jasper", null, con);
            JasperExportManager.exportReportToPdfFile(jp,"AllMovies"+".pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
