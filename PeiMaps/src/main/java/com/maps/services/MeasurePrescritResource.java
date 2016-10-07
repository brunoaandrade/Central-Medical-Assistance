/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.AndroidStuff;
import com.maps.entities.PAction;
import com.maps.entities.PrescriptedMeasureAction;
import static com.maps.entitiesManager.MobileRegister.getList;
import com.maps.entitiesManager.PrescriptedMeasureActionManager;
import com.maps.utilities.SendMessage;
import java.util.List;
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
@Path("measurePrescrit")
public class MeasurePrescritResource {

    private static final Gson gson = new GsonBuilder().create();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MeasurePrescritResource
     */
    public MeasurePrescritResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.MeasurePrescritResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of MeasurePrescritResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String putJson(String content) {
        
        /**
         * RECEBE: 
         *  PrescriptedAction
         *  MeasurePrescript
         * 
         * PrescriptedMeasureAction
         * 
         * 
         * Call SP
         * 
         * 
         * 
         * Send 
         * 
         */
        
        PrescriptedMeasureAction prescriptedMeasureAction = gson.fromJson(content, PrescriptedMeasureAction.class);
        
        if(PrescriptedMeasureActionManager.addPrescriptedMeasureActionManager(prescriptedMeasureAction)){
            //SEND MESSAGE
            
            //(int actionId,ActionType actionType, MeasureType measureType, String timeToStart, int period, int numberreps, String description)
            PAction pAction = new PAction();
            
            pAction.setActionId(prescriptedMeasureAction.getMeasurePrescript().getIdMeasure());
            pAction.setActionType(PAction.ActionType.Measure);
            if(prescriptedMeasureAction.getMeasurePrescript().getMeasureType().toString().equalsIgnoreCase("Blood")){
                pAction.setMeasureType(PAction.MeasureType.Blood);
            }
            else if(prescriptedMeasureAction.getMeasurePrescript().getMeasureType().toString().equalsIgnoreCase("Temperature")){
                pAction.setMeasureType(PAction.MeasureType.Temperature);
            }
            pAction.setNumberreps(prescriptedMeasureAction.getPrescriptedAction().getNumberreps());
            pAction.setPeriod(prescriptedMeasureAction.getPrescriptedAction().getPeriod());
            pAction.setTimeToStart(prescriptedMeasureAction.getPrescriptedAction().getTimeToStart().toString());
            
            /**
             *   prescriptedMeasureAction.getMeasurePrescript().getIdMeasure(),
                                            ActionType.Measure, 
                                            MeasureType.Blood, 
                                            prescriptedMeasureAction.getPrescriptedAction().getTimeToStart().toString(), 
                                            prescriptedMeasureAction.getPrescriptedAction().getPeriod(),
                                            prescriptedMeasureAction.getPrescriptedAction().getNumberreps(),
                                            ""
             */
            
            System.out.println("\n\n*************\n"+pAction.toString()+"\n"+prescriptedMeasureAction.getPatientAction().toString());
            
            AndroidStuff androidStuff = new AndroidStuff(pAction);
            System.out.println(pAction);
            List<String> mylist = getList(prescriptedMeasureAction.getPatientAction().getIdPatient());
            for(String tmp : mylist){
                SendMessage.sendMessageMobile(androidStuff, prescriptedMeasureAction.getPatientAction().getIdPatient(),tmp);
            
            }
            
            //SendMessage.sendMessage(pAction, prescriptedMeasureAction.getPatientAction().getIdPatient());
            
            return "BYE "+prescriptedMeasureAction.getMeasurePrescript().getIdMeasure();
            
        }
        
        
        
        
        return "HI";
    }
}
