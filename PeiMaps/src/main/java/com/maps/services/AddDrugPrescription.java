/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.maps.entities.AndroidStuff;
import com.maps.entities.DrugTakePrescripted;
import com.maps.entities.PAction;
import com.maps.entities.PrescriptedDrugAction;
import static com.maps.entitiesManager.DrugTakePrescriptedManager.addPrescriptedDrugActionManager;
import static com.maps.entitiesManager.MobileRegister.getList;
import com.maps.utilities.SendMessage;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author macnash
 */

@Path("AddDrugPrescription")
public class AddDrugPrescription {

    
    private static final Gson gson = new GsonBuilder().create();
    
    @Context
    private UriInfo context;
    
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String putJson(String content) 
    {
         try
          {
             PrescriptedDrugAction dtp = gson.fromJson(content, PrescriptedDrugAction.class);
             boolean stat = addPrescriptedDrugActionManager(dtp);
             
             if(stat)
             {
                PAction pAction = new PAction();
                
                pAction.setActionId(dtp.getDrugTakePrescripted().getIdTake());
                pAction.setActionType(PAction.ActionType.Drugtake);
                pAction.setNumberreps(dtp.getPrescriptedAction().getNumberreps());
                pAction.setPeriod(dtp.getPrescriptedAction().getPeriod());
                pAction.setTimeToStart(dtp.getPrescriptedAction().getTimeToStart().toString());
                pAction.setDrugName(dtp.getDrugTakePrescripted().getDrugName());
                pAction.setDescription(dtp.getDrugTakePrescripted().getDescription());
                
                AndroidStuff androidStuff = new AndroidStuff(pAction);
                System.out.println(dtp);
                
                List<String> mylist = getList(dtp.getPatientAction().getIdPatient());

                for(String tmp : mylist){
                    System.out.println("\n****************"+tmp);
                    SendMessage.sendMessageMobile(androidStuff, dtp.getPatientAction().getIdPatient(),tmp);           
                }
                
                 return "Success" ;
             }
             else
             {
                 return "Sql error";
             } 
             
            }
             catch(JsonSyntaxException ex)
             {
               return "Invalid Json";
             }
       }
}
