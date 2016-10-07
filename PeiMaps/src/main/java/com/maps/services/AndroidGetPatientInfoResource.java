/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.PAction;
import com.maps.entities.Patient;
import com.maps.entitiesManager.PatientManager;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("androidGetPatientInfo")
public class AndroidGetPatientInfoResource {

    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AndroidGetPatientInfoResource
     */
    public AndroidGetPatientInfoResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.AndroidGetPatientInfoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int id) {
        
        try {
            Gson gson = new GsonBuilder().create();
            
            
            CallableStatement sStmt = myCon.getCon().prepareCall("SELECT * FROM Patient as P WHERE P.idPatient="+id);
            ResultSet rs = sStmt.executeQuery();
            Patient patient = new Patient();
            if(rs.next())
            {
                patient.setIdPatient(rs.getInt("idPatient"));
                patient.setSns(rs.getInt("sns"));
                patient.setGender(rs.getString("gender"));
                patient.setMail(rs.getString("mail"));
                patient.setFname(rs.getString("fname"));
                patient.setLname(rs.getString("lname"));
                //patient.setIdHealthCare(rs.getInt("idHealthCare"));
                patient.setBorndate(rs.getDate("borndate"));
                patient.setCity(rs.getString("city"));
                
            }
            
            //Patient patient = PatientManager.getPatientByToken(patientToken);
            System.out.println(patient);
            return gson.toJson(patient);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * PUT method for updating or creating an instance of AndroidGetPatientInfoResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
