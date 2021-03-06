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
import com.maps.entities.HealthCareToken;
import com.maps.entitiesManager.HealthCareManager;
import com.maps.entitiesManager.HealthCareTokenManager;
import com.maps.utilities.PasswordHasher;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("healthcarelogin")
public class HealthCareLoginResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HealthCareLoginResource
     */
    public HealthCareLoginResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.HealthCareLoginResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of HealthCareLoginResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String putJson(String content) {
        HealthCareToken token=null;
        Gson gson = new GsonBuilder().create();
        try{
            HealthCare healthcare = gson.fromJson(content, HealthCare.class);
            if(healthcare==null){
                return null;    //"Error Creating patient!";
            }
            else if(healthcare.getPasswordHash().isEmpty()){
                return null;    //"No password inserted!";
            }
            
            //Hashing the Patient password
            healthcare.setPasswordHash(PasswordHasher.hashedPW(healthcare.getPasswordHash()));
            
            if(HealthCareManager.isValidLogin(healthcare)){
                token = HealthCareTokenManager.getToken(healthcare);
            }
            return gson.toJson(token);
        }catch(JsonSyntaxException ex){
            return "Invalid Json";
        }
    }
}
