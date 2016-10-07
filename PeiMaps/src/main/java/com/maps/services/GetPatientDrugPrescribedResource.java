/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entitiesManager.DrugTakePrescriptedManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("getPatientDrugPrescribed")
public class GetPatientDrugPrescribedResource {

    
    Gson gson = new GsonBuilder().create();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetPatientDrugPrescribedResource
     */
    public GetPatientDrugPrescribedResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetPatientDrugPrescribedResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int req) {
        return gson.toJson(DrugTakePrescriptedManager.getPatientDrug(req));   
    }

    /**
     * PUT method for updating or creating an instance of GetPatientDrugPrescribedResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
