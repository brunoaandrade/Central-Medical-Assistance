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
import com.maps.entitiesManager.PatientManager;
import com.maps.utilities.PasswordHasher;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author MoLt1eS
 */
@Path("patientregister")
public class PatientRegister {
  
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PatientLogin
     */
    public PatientRegister() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.PatientLogin
     * @return an instance of java.lang.String
     * 
     * Só para testar!!!
     * A REMOVER
     */
//    @GET
//    @Produces("text/plain")
//    public String getJson() {
//        Patient patient = new Patient(29493992, "validemail@ua.pt", PasswordHasher.hashedPW("AMinhaPw"), "Jonny", "Bravo");
//        
//        return "Tring to add:\n"+patient.toString()+"\n Status:"+PatientManager.addPatient(patient);
//    }

    /**
     * PUT method for updating or creating an instance of PatientLogin
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     * 
     * A mudar:
     * @Produces
     * Valor de retorno
     */
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String putJson(String content) {
        
        Gson gson = new GsonBuilder().create();
        try{
            Patient patient = gson.fromJson(content, Patient.class);
            if(patient==null){
                return "Error Creating patient!";
            }
            else if(patient.getPasswordHash()==null){
                return "No password inserted!";
            }
            //Hashing the Patient password
            patient.setPasswordHash(PasswordHasher.hashedPW(patient.getPasswordHash()));
            
            //A TESTAR
            //PatientManager pm = new PatientManager();
            return "\n Status:"+PatientManager.addPatient(patient);
        }catch(JsonSyntaxException ex){
            return "Invalid Json";
        }
    }
}
