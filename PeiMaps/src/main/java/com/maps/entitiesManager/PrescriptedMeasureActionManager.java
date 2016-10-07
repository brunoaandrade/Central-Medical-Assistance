/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.PrescriptedMeasureAction;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class PrescriptedMeasureActionManager {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    public static boolean addPrescriptedMeasureActionManager(PrescriptedMeasureAction prescriptedMeasureAction){
        
    try {
            if(prescriptedMeasureAction==null){
                System.out.println("Entrada nulla no prescriptedMeasureAction");
                return false;
            }
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.insertPrescriptedMeasure);
            cStmt.setInt(1, prescriptedMeasureAction.getPatientAction().getIdPatient());
            cStmt.setInt(2, prescriptedMeasureAction.getPrescriptedAction().getIdHealthCare());
            cStmt.setTimestamp(3, prescriptedMeasureAction.getPrescriptedAction().getTimeToStart());
            cStmt.setInt(4, prescriptedMeasureAction.getPrescriptedAction().getPeriod());
            cStmt.setInt(5, prescriptedMeasureAction.getPrescriptedAction().getNumberreps());
            cStmt.setString(6, prescriptedMeasureAction.getMeasurePrescript().getMeasureType().toString());
            cStmt.registerOutParameter(7,Types.INTEGER);
            cStmt.registerOutParameter(8,Types.INTEGER);
            cStmt.execute();
            prescriptedMeasureAction.getMeasurePrescript().setIdMeasure(cStmt.getInt(8));
            return cStmt.getInt(7)==0;
        } 
    catch (SQLException ex) 
        {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            ex.printStackTrace();
            return false;
        }
    }
}
