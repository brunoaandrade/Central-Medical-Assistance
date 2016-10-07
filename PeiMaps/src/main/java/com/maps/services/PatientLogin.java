/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.maps.entities.Patient;
import com.maps.entities.PatientToken;
import com.maps.entitiesManager.MobileRegister;
import com.maps.entitiesManager.PatientManager;
import com.maps.entitiesManager.PatientTokenManager;
import com.maps.utilities.PasswordHasher;
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
 * @author ubuntu
 */
@Path("patientlogin")
public class PatientLogin {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PatientLogin
     */
    public PatientLogin() {
    }

    /**
     * Retrieves representation of an instance of com.maps.entities.PatientLogin
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of PatientLogin
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String putJson(@QueryParam("dev_id") String androidID, String content) {
        PatientToken token=null;
        Gson gson = new GsonBuilder().create();
        try{
            Patient patient = gson.fromJson(content, Patient.class);
            if(patient==null){
                return null;    //"Error Creating patient!";
            }
            else if(patient.getPasswordHash()==null){
                return null;    //"No password inserted!";
            }
            
            //Hashing the Patient password
            patient.setPasswordHash(PasswordHasher.hashedPW(patient.getPasswordHash()));
            
            if(PatientManager.isValidLogin(patient)){
                token = PatientTokenManager.getToken(patient);
            }
            
            //patient+androidID
            //verificar se ja existe
            //if not : add
            if(MobileRegister.checkMobile(androidID, patient.getIdPatient())){
                MobileRegister.addMobile(androidID, patient.getIdPatient());
            }
            
            return gson.toJson(token);
        }catch(JsonSyntaxException ex){
            return "Invalid Json";
        }
    }
}
