/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.AndroidStuff;
import com.maps.entities.DrugTakeRegister;
import static com.maps.entitiesManager.DrugTakeRegisterManager.addDrugTakeRegisterManager;
import com.maps.entitiesManager.MobileRegister;
import com.maps.entitiesManager.PatientManager;
import com.maps.utilities.SendMessage;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("registerDrugTake")
public class RegisterDrugTakeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RegisterDrugTakeResource
     */
    public RegisterDrugTakeResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.RegisterDrugTakeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RegisterDrugTakeResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String putJson(
            @DefaultValue("") @QueryParam("token") String patientToken,
            @DefaultValue("") @QueryParam("dev_id") String androidID,
            String content) 
    {
        System.out.println("\nCONTEUDO: "+content);
        System.out.println("\nToken: "+patientToken);
        System.out.println("\nAndroidID : "+androidID);
        Gson gson = new GsonBuilder().create();
        int patient_id;
        AndroidStuff androidStuff;
        
        DrugTakeRegister dtr = gson.fromJson(content, DrugTakeRegister.class);
        
        patient_id = PatientManager.getPatientByToken(patientToken).getIdPatient();
        System.out.println("\n\n\n\n\n\n PATIENT: "+patient_id+"\n\n\n\n\n");
        boolean stat = addDrugTakeRegisterManager(dtr);
        System.out.println("\n\n\n\n\n\n Estado: "+stat+"\n\n\n\n\n");
        
        if(patient_id>0 && stat){
            androidStuff = new AndroidStuff(dtr);
            List<String> mobileIds = MobileRegister.getList(patient_id);
            for(String temp : mobileIds){
                System.out.println("\n***************************************"
                        + "\n********************"+temp);
                if(!temp.equals(androidID)){
                    SendMessage.sendMessageMobile(androidStuff, patient_id, temp);
                }
            }
        }
        
        
        return "OK";
    }
}
