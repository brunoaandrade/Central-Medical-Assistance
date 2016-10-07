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
import com.maps.entitiesManager.PatientManager;
import com.maps.entitiesManager.PatientTokenManager;
import com.maps.utilities.PasswordHasher;
import com.maps.utilities.SendMessage;
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
 * @author Tiago
 */
@Path("getPatientToken")
public class GetPatientTokenResource {

    Gson gson = new GsonBuilder().create();
    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetPatientTokenResource
     */
    public GetPatientTokenResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetPatientTokenResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GetPatientTokenResource
     * @param id_mobile
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String putJson(@QueryParam("id_mobile") String id_mobile, String content) {
        PatientToken token=null;
        //Gson gson = new GsonBuilder().create();
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
            if(!token.getToken().isEmpty()){
                SendMessage.sendMessageMobile("HELLO", patient.getIdPatient(), id_mobile);
            }
            return gson.toJson(token);
        }catch(JsonSyntaxException ex){
            return "Invalid Json";
        }
    }
}
