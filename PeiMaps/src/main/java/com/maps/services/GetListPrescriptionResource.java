/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.ActionType;
import static com.maps.entities.ActionType.Activity;
import static com.maps.entities.ActionType.Drugtake;
import static com.maps.entities.ActionType.Measure;
import com.maps.entities.PrescriptedAction;
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
import javax.ws.rs.POST;
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
@Path("getListPrescription")
public class GetListPrescriptionResource {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetListPrescriptionResource
     */
    public GetListPrescriptionResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetListPrescriptionResource
     * @param req
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int req) {
        //some comments
        List<PrescriptedAction> prescriptionList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        try {
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.patientPrescriptions);
            cStmt.setInt(1, req);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();
            //int exitValueFree = cStmt.getInt(2);

            while (rs.next())
            {
                PrescriptedAction prescrition = new PrescriptedAction();
                int idPrescriptact = rs.getInt("idPrescriptact");
                int idAction = rs.getInt("idAction");
                int idHealthCare = rs.getInt("idHealthCare");
                Timestamp timeToStart = rs.getTimestamp("timeToStart");
                
                ActionType actionType = null;
                if(rs.getString("actionType").equals(Measure.toString())){
                    actionType=Measure;                    
                }
                if(rs.getString("actionType").equals(Drugtake.toString())){
                    actionType=Drugtake;                    
                }
                if(rs.getString("actionType").equals(Activity.toString())){
                    actionType=Activity;                    
                }
                int numberreps = rs.getInt("numberreps");
                int period = rs.getInt("period");
                
                prescrition.setIdPrescriptact(idPrescriptact);
                prescrition.setIdAction(idAction);
                prescrition.setIdHealthCare(idHealthCare);
                prescrition.setTimeToStart(timeToStart);
                prescrition.setActiontype(actionType);
                prescrition.setNumberreps(numberreps);
                prescrition.setPeriod(period);
                prescriptionList.add(prescrition);

            }
            
            return gson.toJson(prescriptionList);
        
        }catch (SQLException ex) {
            Logger.getLogger(GetPatientListResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    /**
     * PUT method for updating or creating an instance of GetListPrescriptionResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
