/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.MeasurePrescript;
import com.maps.entities.MeasureType;
import static com.maps.entities.MeasureType.Blood;
import static com.maps.entities.MeasureType.Temperature;
import static com.maps.entities.MeasureType.HeartRate;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("getListMeasurePrescript")
public class GetListMeasurePrescriptResource {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetListMeasurePrescriptResource
     */
    public GetListMeasurePrescriptResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetListMeasurePrescriptResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int req) {
        
        List<MeasurePrescript> measureList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        try {
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.measureprescriptList);
            cStmt.setInt(1, req);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();
            
            while (rs.next())
            {
                MeasurePrescript measure = new MeasurePrescript();
                int idMeasure = rs.getInt("idMeasure");
                
                MeasureType measureType = null;
                if(rs.getString("measureType").equals(Blood.toString())){
                    measureType=Blood;                    
                }
                if(rs.getString("measureType").equals(Temperature.toString())){
                    measureType=Temperature;                    
                }
                if(rs.getString("measureType").equals(HeartRate.toString())){
                    measureType=HeartRate;                    
                }
                
                int idPrescriptact = rs.getInt("idPrescriptact");
                Timestamp timeMeasurePrescript = rs.getTimestamp("timeMeasurePrescript");
               
                
                measure.setIdMeasure(idMeasure);
                measure.setMeasureType(measureType);
                measure.setIdPrescriptact(idPrescriptact);
                measure.setTimeMeasurePrescript(timeMeasurePrescript);
                measureList.add(measure);
              
            }
            
            return gson.toJson(measureList);
            
        } catch (SQLException ex) {
            Logger.getLogger(GetPatientListResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO return proper representation object
        return null;
        
    }

    /**
     * PUT method for updating or creating an instance of GetListMeasurePrescriptResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
