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
import com.maps.entities.PAction;
import com.maps.entities.PrescribedActivity;
import static com.maps.entitiesManager.MobileRegister.getList;
import com.maps.entitiesManager.PrescribedActivityManager;
import com.maps.utilities.SendMessage;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author macnash
 */
@Path("PrescribeActivity")
public class PrescribeActivityRes {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PrescribeActivityRes
     */
    public PrescribeActivityRes() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.PrescribeActivityRes
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int req) 
    {
        return PrescribedActivityManager.getPatientActivitys(req);
    }

    /**
     * PUT method for updating or creating an instance of PrescribeActivityRes
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String putJson(String content) 
    {
        
        System.out.println("\n\n\n*********"+content+"\n*********\n\n\n");
      boolean stat;
      
        Gson gson = new GsonBuilder().create();
        try
        {
            PrescribedActivity presact = gson.fromJson(content, PrescribedActivity.class);
            if (presact == null)
            {
                return " Object Activity is NULL";
            }
            
            stat = PrescribedActivityManager.addPrescribedActivity(presact);
            if(stat)
            {
                
                PAction pAction = new PAction();
                
                pAction.setActionId(presact.getPrescriptedAction().getIdAction());
                pAction.setActionType(PAction.ActionType.Activity);
                pAction.setNumberreps(presact.getPrescriptedAction().getNumberreps());
                pAction.setPeriod(presact.getPrescriptedAction().getPeriod());
                pAction.setTimeToStart(presact.getPrescriptedAction().getTimeToStart().toString());
                pAction.setDescription(presact.getActivity().getDescription());
                
                AndroidStuff androidStuff = new AndroidStuff(pAction);
                System.out.println(presact);
                
                List<String> mylist = getList(presact.getPatientAction().getIdPatient());

                for(String tmp : mylist){
                    System.out.println("\n****************"+tmp);
                    SendMessage.sendMessageMobile(androidStuff, presact.getPatientAction().getIdPatient(),tmp);           
                }
                return "Success";
            }
              return "Sql Error";
         }
          catch(JsonSyntaxException ex)
          {
            return "Json is not well formed";
        }
    }
}
