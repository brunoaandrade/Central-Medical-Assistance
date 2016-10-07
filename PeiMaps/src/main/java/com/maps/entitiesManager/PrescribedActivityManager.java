/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.ActionType;
import com.maps.entities.Activity;
import com.maps.entities.DrugTakePrescripted;
import com.maps.entities.PrescribedActivity;
import com.maps.services.PrescribeActivityRes;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macnash
 */
public class PrescribedActivityManager 
{
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    public static String getPatientActivitys (int patientid)
    {
        List<Activity> ActivitysList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        
        try
        {
            // Preparing activity list execution
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.activityList);
            cStmt.setInt(1, patientid);
            cStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();  //Executing SP
            
            // iterating over rs
            while (rs.next())
            {
                // Creating new Activity to send back
                Activity activ = new Activity();
                int idActiv = rs.getInt("idActivity");
                int idAction = rs.getInt("idAction");
                String description = rs.getString("description");
                // filling it up
                activ.setIdActivity(idActiv);
                activ.setIdAction(idAction);
                activ.setDescription(description);
                //Adding it to the Activity list
                ActivitysList.add(activ);
            }
              return gson.toJson(ActivitysList);
              
        }
         catch (SQLException ex) 
         {
            Logger.getLogger(PrescribedActivity.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        return null;
                
     }
       
    
            
    
    
    public static boolean addPrescribedActivity (PrescribedActivity prescAct)
    {
        
        try
        {
           if(prescAct == null)
            {
                System.out.println("Null PrescribedActivity");
                return false;
            }
             /*
              Filling PrescribedActivity class with data receivd as parameter 
               With SP called insertPrescriptedActivity(?,?,?,?,?,?) format
                insertPrescriptedActivity (in idPatient int(11), 
                 in idHealthCare int(11), in timeToStart TIMESTAMP, in description varchar (5000),
                 out result int, out idActivity int(11))
           */
           
            CallableStatement cStm = myCon.getCon().prepareCall(SPCall.insertPrescriptedActivity);
            
            cStm.setInt(1, prescAct.getPatientAction().getIdPatient());
            cStm.setInt(2, prescAct.getPrescriptedAction().getIdHealthCare());
            cStm.setTimestamp(3, prescAct.getPrescriptedAction().getTimeToStart());
            cStm.setString(4, prescAct.getActivity().getDescription());
            cStm.registerOutParameter(5, Types.INTEGER);
            cStm.registerOutParameter(6, Types.INTEGER);
            
            cStm.execute();
            prescAct.getActivity().setIdActivity(cStm.getInt(6));
            
            return cStm.getInt(5) == 0;
            
        }
          catch (SQLException ex) 
          {
            Logger.getLogger(PrescribedActivity.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(); //Sql Error
            return false;
          }
                
    }
    
}
