/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.ActionType;
import static com.maps.entities.ActionType.Activity;
import static com.maps.entities.ActionType.Drugtake;
import static com.maps.entities.ActionType.Measure;
import com.maps.entities.Activity;
import com.maps.entities.PrescriptedAction;
import com.maps.services.GetPatientListResource;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class ActivityManager {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    public static Activity getPatientActivity(int id){
        Activity activity = new Activity();
        ActionType actionType = ActionType.Activity;
        
        try {
            /**
             * listActiondetails(in idAction, in actiontype, out result)
             * Add latter after update
             * listActiondetails(?,?,?);
             */
            CallableStatement cStmt = myCon.getCon().prepareCall("listActiondetails(?,?,?)");
            cStmt.setInt(1, id);
            cStmt.setString(2,actionType.toString());
            cStmt.registerOutParameter(3, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();

            while (rs.next())
            {
                
                int idActivity = rs.getInt("idActivity");
                int idAction = rs.getInt("idAction");
                String description = rs.getString("description");
                
                activity.setIdActivity(idActivity);
                activity.setIdAction(idAction);
                activity.setDescription(description);

            }
            
        }catch (SQLException ex) {
            Logger.getLogger(GetPatientListResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        return activity;
    }
}
