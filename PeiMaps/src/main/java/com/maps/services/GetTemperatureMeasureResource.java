/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.TemperatureMeasure;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Kensishi
 */
@Path("getTemperatureMeasure")
public class GetTemperatureMeasureResource {

    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetTemperatureMeasureResource
     */
    public GetTemperatureMeasureResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetTemperatureMeasureResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int idPatient) {
        
        //Retorna as medidas free e prescripted de Temperature
        List<TemperatureMeasure> TMeasureList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        try {
            //FREE----------
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.FreeTempMeasure); //nao encontra
            cStmt.setInt(1, idPatient);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();
            int exitValueFree = cStmt.getInt(2);//guardar parametro de saida depois de correr a SP
            
            while (rs.next())
            {
                TemperatureMeasure temperature = new TemperatureMeasure();
                int idTMeasure = rs.getInt("idTMesure");
                int idMeasure = rs.getInt("idMeasurefree");
                int idMeasurepresvript = rs.getInt("idMeasureprescript");
                int tMeasure = rs.getInt("tMeasure");
                Timestamp timeMeasured = rs.getTimestamp("timeMeasured");
               
                
                temperature.setIdTMeasure(idTMeasure);
                temperature.setIdMeasure(idMeasure);
                temperature.setIdMeasurepresvript(idMeasurepresvript);
                temperature.settMeasure(tMeasure);
                temperature.setTimeMeasured(timeMeasured);
               
                TMeasureList.add(temperature);
            }
            
            //PRESCRIPTED-------
            cStmt = myCon.getCon().prepareCall(SPCall.PrescriptTempMeasure); //nao encontra
            cStmt.setInt(1, idPatient);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            rs = cStmt.executeQuery();
            int exitValuePrescript = cStmt.getInt(2);//guardar parametro de saida depois de correr a SP
            
            while (rs.next())
            {
                TemperatureMeasure temperature = new TemperatureMeasure();
                int idTMeasure = rs.getInt("idTMesure");
                int idMeasure = rs.getInt("idMeasurefree");
                int idMeasurepresvript = rs.getInt("idMeasureprescript");
                int tMeasure = rs.getInt("tMeasure");
                Timestamp timeMeasured = rs.getTimestamp("timeMeasured");
               
                
                temperature.setIdTMeasure(idTMeasure);
                temperature.setIdMeasure(idMeasure);
                temperature.setIdMeasurepresvript(idMeasurepresvript);
                temperature.settMeasure(tMeasure);
                temperature.setTimeMeasured(timeMeasured);
               
                TMeasureList.add(temperature);
            }
            
            //tratar o parametro de saida
            if (exitValueFree == 2 && exitValuePrescript == 2)          //Unknown SqlException
                return null;
            else if (exitValueFree == 2 && exitValuePrescript == 1)    //Unknown SqlException
                return null;
            else if (exitValueFree == 1 && exitValuePrescript == 2)    //Unknown SqlException
                return null;
            else if (exitValueFree == 1 && exitValuePrescript == 1)    //no measures for this patient
                return null;
            else   //exitValueFree = 0 ||  exitValuePrescript = 0 success (sendo um dos exitValue 0, tratar da combinaçao 0-1, 0-2, 1-0, 2-0 ??)
                return gson.toJson(TMeasureList);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(GetBPMeasureResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * PUT method for updating or creating an instance of GetTemperatureMeasureResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
