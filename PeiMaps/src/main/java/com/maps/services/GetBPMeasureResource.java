/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.BloodPressureMeasure;
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
@Path("getBPMeasure")
public class GetBPMeasureResource {

    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetBPMeasureResource
     */
    public GetBPMeasureResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetBPMeasureResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int idPatient) {
        //Retorna as medidas free e prescripted de BloodPressure
        
        List<BloodPressureMeasure> BPMeasureList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        try {
            //FREE----------
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.getFreeBPMeasure); //nao encontra
            cStmt.setInt(1, idPatient);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();
            int exitValueFree = cStmt.getInt(2);//guardar parametro de saida depois de correr a SP
            
            while (rs.next())
            {
                BloodPressureMeasure bloodPressure = new BloodPressureMeasure();
                int idBPMeasure = rs.getInt("idBPMeasure");
                int idMeasurefree = rs.getInt("idMeasurefree");
                int idMeasureprescript = rs.getInt("idMeasureprescript");
                int dMeasure = rs.getInt("dMeasure");
                int sMeasure = rs.getInt("sMeasure");
                int freqMeasure = rs.getInt("freqMeasure");
                Timestamp timeMeasured = rs.getTimestamp("timeMeasured");
                

                bloodPressure.setIdBPMeasure(idBPMeasure);
                bloodPressure.setIdMeasurefree(idMeasurefree);
                bloodPressure.setIdMeasureprescript(idMeasureprescript);
                bloodPressure.setdMeasure(dMeasure);
                bloodPressure.setsMeasure(sMeasure);
                bloodPressure.setFreqMeasure(freqMeasure);
                bloodPressure.setTimeMeasured(timeMeasured);
                BPMeasureList.add(bloodPressure);
            }
            
            //PRESCRIPTED-------
            cStmt = myCon.getCon().prepareCall(SPCall.getPrescriptBPMeasure); //nao encontra
            cStmt.setInt(1, idPatient);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            rs = cStmt.executeQuery();
            int exitValuePrescript = cStmt.getInt(2);//guardar parametro de saida depois de correr a SP
            
            while (rs.next())
            {
                BloodPressureMeasure bloodPressure = new BloodPressureMeasure();
                int idBPMeasure = rs.getInt("idBPMeasure");
                int idMeasurefree = rs.getInt("idMeasurefree");
                int idMeasureprescript = rs.getInt("idMeasureprescript");
                int dMeasure = rs.getInt("dMeasure");
                int sMeasure = rs.getInt("sMeasure");
                int freqMeasure = rs.getInt("freqMeasure");
                Timestamp timeMeasured = rs.getTimestamp("timeMeasured");
                

                bloodPressure.setIdBPMeasure(idBPMeasure);
                bloodPressure.setIdMeasurefree(idMeasurefree);
                bloodPressure.setIdMeasureprescript(idMeasureprescript);
                bloodPressure.setdMeasure(dMeasure);
                bloodPressure.setsMeasure(sMeasure);
                bloodPressure.setFreqMeasure(freqMeasure);
                bloodPressure.setTimeMeasured(timeMeasured);
                BPMeasureList.add(bloodPressure);
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
                return gson.toJson(BPMeasureList);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(GetBPMeasureResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * PUT method for updating or creating an instance of GetBPMeasureResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
