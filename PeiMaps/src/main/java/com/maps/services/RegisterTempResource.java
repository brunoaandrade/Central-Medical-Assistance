/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.AndroidStuff;
import com.maps.entities.TemperatureMeasure;
import com.maps.entitiesManager.MobileRegister;
import com.maps.entitiesManager.PatientManager;
import com.maps.entitiesManager.TemperatureMeasureManager;
import com.maps.utilities.SendMessage;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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
@Path("registerTemp")
public class RegisterTempResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RegisterTempResource
     */
    public RegisterTempResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.RegisterTempResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RegisterTempResource
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
        
        TemperatureMeasure tempM = gson.fromJson(content, TemperatureMeasure.class);
        
        System.out.println(PatientManager.getPatientByToken(patientToken));
        patient_id = PatientManager.getPatientByToken(patientToken).getIdPatient();
        
        System.out.println("\n The id of Prescript is " + tempM.getIdMeasurepresvript());
        
        //Se for uma mediçao prescrita
        if(tempM.getIdMeasurepresvript()>0){
            //Se inserio a mediçao prescrita
            if(TemperatureMeasureManager.addPrescriptedMeasure(tempM)){
                //Se o numero de devices for maior que 1
                //Envia mensagem para os outros devices excepto o que chamou este serviço (androidID);
                androidStuff = new AndroidStuff(tempM);
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

            //se for uma mediçao free
            //buscar o ID do patient

            
            System.out.println(
                      "\n**********\n********\n*******"
                    + "PATIENT ID: " + patient_id
                    + "\n**********\n********\n*******");
            //Se addicionou a mediçao free
            if(TemperatureMeasureManager.addFreeMeasure(tempM, patient_id)){
                //Se o numero de devices for maior que 1
                androidStuff = new AndroidStuff(tempM);
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
