/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.ActionType;
import com.maps.entities.MeasurePrescript;
import com.maps.entities.MeasureType;
import com.maps.services.GetPatientListResource;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class MeasurePrescriptManager {
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    public static MeasurePrescript getPatientMeasure(int id){
        MeasurePrescript measureP = new MeasurePrescript();
        ActionType actionType = ActionType.Measure;
        
        try {
            /**
             * listActiondetails(in idAction, in actiontype, out result)
             * Add latter after update
             * listActiondetails(?,?,?);
             */
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.listActiondetails);
            cStmt.setInt(1, id);
            cStmt.setString(2,actionType.toString());
            cStmt.registerOutParameter(3, java.sql.Types.INTEGER);
            
            ResultSet rs = cStmt.executeQuery();
            //int exitValue = cStmt.getInt(3);
            
            while (rs.next())
            {
                measureP.setIdMeasure(rs.getInt("idMeasure"));
                String type = rs.getString("measureType");
                
                if(type.equalsIgnoreCase("Blood")){
                    measureP.setMeasureType(MeasureType.Blood); 
                }
                else if(type.equalsIgnoreCase("Temperature")){
                    measureP.setMeasureType(MeasureType.Temperature); 
                }
                
                measureP.setIdPrescriptact(rs.getInt("idPrescriptact"));
                measureP.setTimeMeasurePrescript(rs.getTimestamp("timeMeasurePrescript"));
            }
            
             return measureP;

        }catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(MeasurePrescriptManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
       
    }    
}
