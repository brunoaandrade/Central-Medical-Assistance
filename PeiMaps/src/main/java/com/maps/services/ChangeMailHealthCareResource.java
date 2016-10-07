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
import static com.maps.entitiesManager.HealthCareManager.changeMailHealthCare;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("changeMailHealthCare")
public class ChangeMailHealthCareResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ChangeMailHealthCareResource
     */
    public ChangeMailHealthCareResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.ChangeMailHealthCareResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ChangeMailHealthCareResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String putJson(String content) {
        
        try{
            Gson gson = new GsonBuilder().create();
            HealthCare healthCare = gson.fromJson(content, HealthCare.class);
            int status = changeMailHealthCare(healthCare);
            
            if(status==-3 || status==2){
                return "sql error";
                }
            else if(status==-1){
                return "No HealthCare";
                }
            else {//status = 0
                return "Success" ;
                }         
        }catch(JsonSyntaxException ex){
            return "Invalid Json";
        }
    }
}
