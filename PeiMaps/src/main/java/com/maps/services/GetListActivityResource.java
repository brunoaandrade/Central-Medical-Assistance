/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.Activity;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@Path("getListActivity")
public class GetListActivityResource {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetListActivityResource
     */
    public GetListActivityResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetListActivityResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int req) {
        
        List<Activity> activityList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        try {
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.activityList);
            cStmt.setInt(1, req);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();
            
            while (rs.next())
            {
                Activity activity = new Activity();
                int idActivity = rs.getInt("idActivity");
                int idAction = rs.getInt("idAction");
                String description = rs.getString("description");
                           
                activity.setIdActivity(idActivity);
                activity.setIdAction(idAction);
                activity.setDescription(description);
                activityList.add(activity);
              
            }
            
            return gson.toJson(activityList);
            
        } catch (SQLException ex) {
            Logger.getLogger(GetPatientListResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO return proper representation object
        return null;
    }

    /**
     * PUT method for updating or creating an instance of GetListActivityResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
