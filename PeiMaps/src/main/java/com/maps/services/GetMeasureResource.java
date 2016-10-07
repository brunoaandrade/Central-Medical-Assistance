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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("getmeasure")
public class GetMeasureResource {
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetMeasureResource
     */
    public GetMeasureResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetMeasureResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        
        List<TemperatureMeasure> tempList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();    
        try {
            Statement cStmt = myCon.getCon().createStatement();
            ResultSet rs = cStmt.executeQuery("SELECT * FROM TemperatureMeasure");
            
            while (rs.next())
            {
                TemperatureMeasure temp = new TemperatureMeasure();
                temp.settMeasure(rs.getInt("tMeasure"));
                temp.setTimeMeasured(rs.getTimestamp("timeMeasured"));

               

                tempList.add(temp);
              
            }
            //TODO return proper representation object
            return gson.toJson(tempList);
        } catch (SQLException ex) {
            Logger.getLogger(GetMeasureResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * PUT method for updating or creating an instance of GetMeasureResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
