/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.Patient;
import com.maps.entitiesManager.PatientManager;
import com.maps.entitiesManager.PatientTokenManager;
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
 * @author user02
 */
@Path("patient")
public class PatientResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PatientResource
     */
    public PatientResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.entities.PatientResource
     * @return 
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("from") String token) {
        
        //Se o token for vazio ou nao existente pára
        if(token==null || PatientTokenManager.checkToken(token)){
            return null;
        }
        
        //SE O TOKEN E VALIDO ENTAO O PACIENTE EXISTEs
        //CRIA PACIENTE A PARTIR DO TOKEN
        Patient patient = PatientManager.getPatientByToken(token);
        
        
        //ENVIAR OBJECT PACIENTE
        Gson gson = new GsonBuilder().create();
        return gson.toJson(patient);
    }

    /**
     * PUT method for updating or creating an instance of PatientResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
