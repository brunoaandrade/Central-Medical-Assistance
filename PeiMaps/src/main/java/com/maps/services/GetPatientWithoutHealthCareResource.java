/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.Patient;
import com.maps.sqlcon.BDPathCon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("GetPatientWithoutHealthCare")
public class GetPatientWithoutHealthCareResource {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetPatientWithoutHealthCareResource
     */
    public GetPatientWithoutHealthCareResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetPatientWithoutHealthCareResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        
        List<Patient> patientList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        try {
            Statement cStmt = myCon.getCon().createStatement();
            ResultSet rs = cStmt.executeQuery("SELECT * FROM Patient WHERE Patient.idHealthCare is null");
            
            while (rs.next())
            {
                Patient patient = new Patient();
                int id = rs.getInt("idPatient");
                int sns = rs.getInt("sns");
                String gender = rs.getString("gender");
                String mail = rs.getString("mail");
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                String history = rs.getString("history");

                patient.setIdPatient(id);
                patient.setSns(sns);
                patient.setMail(mail);
                patient.setFname(fName);
                patient.setLname(lName);
                patient.setGender(gender);
                patient.setHistory(history);
                patientList.add(patient);
              
            }
            
            return gson.toJson(patientList);
            
        } catch (SQLException ex) {
            Logger.getLogger(GetPatientListResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO return proper representation object
        return null;
    }

    /**
     * PUT method for updating or creating an instance of GetPatientWithoutHealthCareResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
