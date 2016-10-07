/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.DrugTakePrescripted;
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
@Path("getListDrugTake")
public class GetListDrugTakeResource {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetListDrugTakeResource
     */
    public GetListDrugTakeResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetListDrugTakeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int req) {
        
        List<DrugTakePrescripted> drugTakeList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        try {
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.drugTakeList);
            cStmt.setInt(1, req);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();
            
            while (rs.next())
            {
                DrugTakePrescripted drugTake = new DrugTakePrescripted();
                int idTake = rs.getInt("idTake");
                int idAction = rs.getInt("idAction");
                Timestamp takeTime = rs.getTimestamp("takeTime"); // um destes nao e necessario
                String drugName = rs.getString("drugName");
                String description = rs.getString("description");

                drugTake.setIdTake(idTake);
                drugTake.setIdAction(idAction);
                drugTake.setTakeTime(takeTime);
                drugTake.setDrugName(drugName);
                drugTake.setDescription(description);
                drugTakeList.add(drugTake);
              
            }
            
            return gson.toJson(drugTakeList);
            
        } catch (SQLException ex) {
            Logger.getLogger(GetListDrugTakeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO return proper representation object
        return null;
    }

    /**
     * PUT method for updating or creating an instance of GetListDrugTakeResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
