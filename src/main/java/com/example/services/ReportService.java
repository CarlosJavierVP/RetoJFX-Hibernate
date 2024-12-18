package com.example.services;

import com.example.CurrentSession;
import net.sf.jasperreports.engine.*;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Clase ReportService que recoge metodos que proporionan servicios, generando informes
 * @author Carlos Javier
 */
public class ReportService {
    private static Connection con;

    public ReportService(Connection c) {con=c;
    }

    /**
     * Metodo generarInformePeliculas genera un informe de todas las peliculas de la base de datos en pdf
     */
    public void generarInformePeliculas(){
        try{
            JasperPrint jp = JasperFillManager.fillReport("listadoPelis.jasper", null, con);
            JasperExportManager.exportReportToPdfFile(jp,"AllMovies.pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo generarInformeCopias para generar un informe de todas las copias del usuario en pdf
     */
    public void generarInformeCopias(){
        HashMap<String, Object> param = new HashMap<>();
        param.put("UsuarioId",CurrentSession.userSelected.getIdUsuario());
        try{
            JasperPrint jp = JasperFillManager.fillReport("listadoCopias.jasper", param, con);
            JasperExportManager.exportReportToPdfFile(jp,"MyCopies.pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo informeCopiasSoporteEstado para generar un informe de copias segun el soporte y estado de las copias
     * @param soporte
     * @param estado
     */
    public void informeCopiasSoporteEstado(String soporte, String estado){
        HashMap<String, Object> params = new HashMap<>();
        params.put("Soporte", soporte);
        params.put("Estado", estado);
        params.put("UsuarioId",CurrentSession.userSelected.getIdUsuario());
        try{
            JasperPrint jp = JasperFillManager.fillReport("copiasSoporteEstado.jasper", params, con);
            JasperExportManager.exportReportToPdfFile(jp,"MyCopies"+soporte+"_"+estado+".pdf");
        }catch (JRException e){
            throw new RuntimeException();
        }
    }

    /**
     * Metodo informeCopias para generar un informe de copias según el parámetro que se elija, soporte o estado
     * @param parametro
     */
    public void informeCopias(String parametro){
        HashMap<String, Object> param = new HashMap<>();
        param.put("UsuarioId",CurrentSession.userSelected.getIdUsuario());
        try{
            if (parametro.contains("DVD") || parametro.contains("Blu-ray")){
                param.put("Soporte", parametro);
                JasperPrint jp = JasperFillManager.fillReport("copiasSoporte.jasper", param, con);
                JasperExportManager.exportReportToPdfFile(jp,"MyCopies"+parametro+".pdf");
            } else if (parametro.contains("dañado") || parametro.contains("bueno")) {
                param.put("Estado", parametro);
                JasperPrint jp = JasperFillManager.fillReport("copiasEstado.jasper", param, con);
                JasperExportManager.exportReportToPdfFile(jp,"MyCopies"+parametro+".pdf");
            }
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void informeMiCopia(Long idCopia){
        HashMap<String, Object> param = new HashMap<>();
        param.put("idCopia", idCopia);
        try{
            JasperPrint jp = JasperFillManager.fillReport("detalleCopia.jasper", param, con);
            JasperExportManager.exportReportToPdfFile(jp,"MiCopiaDetallada.pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
