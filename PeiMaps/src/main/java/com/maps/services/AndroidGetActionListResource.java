/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.PAction;
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
@Path("androidGetActionList")
public class AndroidGetActionListResource {

    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AndroidGetActionListResource
     */
    public AndroidGetActionListResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.AndroidGetActionListResource
     * @return an instance of java.lang.String
     */
    
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int id) {
        
        List<PAction> activityList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        
        try {
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.activityList);
            cStmt.setInt(1, id);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();
            
            while (rs.next())
            {
                PAction activity = new PAction();
                int idActivity = rs.getInt("idActivity");
                int idAction = rs.getInt("idAction");
                String description = rs.getString("description");
                
                activity.setActionId(idActivity);
                activity.setActionType(PAction.ActionType.Activity);
                activity.setDescription(description);
                activity.setTimeToStart(rs.getTimestamp("timeToStart").toString());
                activity.setNumberreps(rs.getInt("numberreps"));
                activity.setPeriod(rs.getInt("period"));
                activityList.add(activity);
            }
            
            CallableStatement sStmt = myCon.getCon().prepareCall(SPCall.measureprescriptList);
            sStmt.setInt(1, id);
            sStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            ResultSet rss = sStmt.executeQuery();
            
            PAction tempA = new PAction();
            PAction bloodA = new PAction();
            while (rss.next())
            {
                PAction activity = new PAction();
                int idAction = rss.getInt("idMeasure");
                String mType = rss.getString("measureType");
                
                activity.setTimeToStart(rss.getTimestamp("timeToStart").toString());
                activity.setActionId(idAction);
                activity.setActionType(PAction.ActionType.Measure);
                activity.setNumberreps(rss.getInt("numberreps"));
                activity.setPeriod(rss.getInt("period"));
                if(mType.equalsIgnoreCase("blood")){
                    activity.setMeasureType(PAction.MeasureType.Blood);
                    bloodA=activity;
                }
                else if(mType.equalsIgnoreCase("temperature")){
                    activity.setMeasureType(PAction.MeasureType.Temperature);
                    tempA=activity;
                }
                //activityList.add(activity);
            }
            
            if(tempA.getActionId()>0)
                activityList.add(tempA);
            if(bloodA.getActionId()>0)
                activityList.add(bloodA);
            
            
            CallableStatement Stmt = myCon.getCon().prepareCall(SPCall.drugTakeList);
            Stmt.setInt(1, id);
            Stmt.registerOutParameter(2, java.sql.Types.INTEGER);
            ResultSet ss = Stmt.executeQuery();
            
            while (ss.next())
            {
                PAction activity = new PAction();
                int idAction = ss.getInt("idTake");
                String desc = ss.getString("description");
                String drugN = ss.getString("drugName");
                
                activity.setTimeToStart(ss.getTimestamp("timeToStart").toString());
                activity.setActionId(idAction);
                activity.setActionType(PAction.ActionType.Drugtake);
                activity.setNumberreps(ss.getInt("numberreps"));
                activity.setDescription(desc);
                activity.setDrugName(drugN);
                activity.setPeriod(ss.getInt("period"));
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
     * PUT method for updating or creating an instance of AndroidGetActionListResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
