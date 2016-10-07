/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.maps.entities.HealthCare;
import com.maps.entitiesManager.HealthCareManager;
import com.maps.utilities.PasswordHasher;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author ubuntu
 */
@Path("healthcareregister")
public class HealthCareRegister {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HealthCareRegister
     */
    public HealthCareRegister() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.HealthCareRegister
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of HealthCareRegister
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String putJson(String content) {
        
        Gson gson = new GsonBuilder().create();
        try{
            HealthCare healthCare = gson.fromJson(content, HealthCare.class);
            if(healthCare==null){
                return "Error Creating patient!";
            }
            else if(healthCare.getPasswordHash()==null){
                return "No password inserted!";
            }
            //Hashing the Patient password
            healthCare.setPasswordHash(PasswordHasher.hashedPW(healthCare.getPasswordHash()));
            
            //A TESTAR
            //PatientManager pm = new PatientManager();
            return "\n Status:"+HealthCareManager.addHealthCare(healthCare);
        }catch(JsonSyntaxException ex){
            return "Invalid Json";
        }
    }
}
