/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.HealthCare;
import com.maps.sqlcon.BDPathCon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@Path("HealthCareInfo")
public class HealthCareInfoResource {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HealthCareInfoResource
     */
    public HealthCareInfoResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.HealthCareInfoResource
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int id) {
                
        List<HealthCare> HealthCareInfo = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        try {
            Statement cStmt = myCon.getCon().createStatement();
            ResultSet rs = cStmt.executeQuery("SELECT * FROM HealthCarePro WHERE idHealthCare="+id);

            while (rs.next())
            {
                HealthCare info = new HealthCare();
                int idHeathCare = rs.getInt("idHealthCare");
                int medicalNumber = rs.getInt("medicalNumber");
                String mail = rs.getString("mail");
                String passwordHash = rs.getString("passwordHash");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                Timestamp addedDate = rs.getTimestamp("addedDate");
                
                info.setIdHeathCare(idHeathCare);
                info.setMedicalNumber(medicalNumber);
                info.setMail(mail);
                info.setPasswordHash(passwordHash);
                info.setFname(fname);
                info.setLname(lname);
                info.setAddedDate(addedDate);
                HealthCareInfo.add(info);

            }
            
            return gson.toJson(HealthCareInfo);
        
        }catch (SQLException ex) {
            Logger.getLogger(GetPatientListResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
       
    }

    /**
     * PUT method for updating or creating an instance of HealthCareInfoResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
