/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entitiesManager.BloodPressureMeasureManager;
import com.maps.entitiesManager.MobileRegister;
import com.maps.entitiesManager.PatientManager;
import com.maps.utilities.SendMessage;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("registerBPM")
public class RegisterBPMResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RegisterBPMResource
     */
    public RegisterBPMResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.entities.RegisterBPMResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RegisterBPMResource
     * @param patientToken
     * @param androidID
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String putJson(
            @DefaultValue("") @QueryParam("token") String patientToken,
            @DefaultValue("") @QueryParam("dev_id") String androidID,
            String content) {

        //Verificar o Token! --
        //Verificar device! --

        AndroidStuff androidStuff;
        
        int patient_id;
        //Debug
        System.out.println(
                  "\n***********************"
                + "\nQueryParam: " + patientToken
                + "\nContent: " + content
                + "\nAndroid ID: " + androidID
                + "\n***********************");

        Gson gson = new GsonBuilder().create();
        System.out.println(PatientManager.getPatientByToken(patientToken));
        patient_id = PatientManager.getPatientByToken(patientToken).getIdPatient();
        //BloodPressureMeasure bpm = gson.fromJson(content, BloodPressureMeasure.class);
        BloodPressureMeasure bpm = gson.fromJson(content, BloodPressureMeasure.class);

        //Se for uma mediçao prescrita
        if(bpm.getIdMeasureprescript()>0){
            //Se inserio a mediçao prescrita
            if(BloodPressureMeasureManager.addPrescriptedBPM(bpm)){
                androidStuff = new AndroidStuff(bpm);
                List<String> mobileIds = MobileRegister.getList(patient_id);
                for(String temp : mobileIds){
                    System.out.println("\n***************************************"
                            + "\n********************"+temp);
                    if(!temp.equals(androidID)){
                        SendMessage.sendMessageMobile(androidStuff, patient_id, temp);
                    }
                }
            }
            else{
                //Caso nao insira retorna error;
                return "ERROR";
            }
        }
        else{   
            if(patientToken.isEmpty())
                return "ERROR_TOKEN";

            

            System.out.println(
                      "\n**********\n********\n*******"
                    + "PATIENT ID: " + patient_id
                    + "\n**********\n********\n*******");
            //Se addicionou a mediçao free
            if(BloodPressureMeasureManager.addFreeBPM(bpm, patient_id)){
                androidStuff = new AndroidStuff(bpm);
                List<String> mobileIds = MobileRegister.getList(patient_id);
                for(String temp : mobileIds){
                    System.out.println("\n***************************************"
                            + "\n********************"+temp);
                    if(!temp.equals(androidID)){
                        SendMessage.sendMessageMobile(androidStuff, patient_id, temp);
                    }
                }
            }
            else{
                //Caso nao insira
                return "ERROR";
            }
        }

        return "OK";
    }


}
