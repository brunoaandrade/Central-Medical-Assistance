/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.BloodPressureMeasure;
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
public class BloodPressureMeasureManager {
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    public static boolean addPrescriptedBPM(BloodPressureMeasure bpm){
        
        try {
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.insertPrescrBloodMeasure);
            cStmt.setInt(1, bpm.getIdMeasureprescript());
            cStmt.setInt(2, bpm.getdMeasure());
            cStmt.setInt(3, bpm.getsMeasure());
            cStmt.setInt(4, bpm.getFreqMeasure());
            cStmt.setTimestamp(5, bpm.getTimeMeasured());
            cStmt.registerOutParameter(6,Types.INTEGER);
            cStmt.execute();
            return cStmt.getInt(6)==0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BloodPressureMeasureManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean addFreeBPM(BloodPressureMeasure bpm, int patient_id) {
        try {
    /*in idPatient int(11), 
    in dMeasure int(11),in sMeasure int(11),in freqMeasure int(11), 
    in timeMeasured timestamp, out result int*/
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.insertFreeBloodPressMeasure);
            cStmt.setInt(1, patient_id);
            cStmt.setInt(2, bpm.getdMeasure());
            cStmt.setInt(3, bpm.getsMeasure());
            cStmt.setInt(4, bpm.getFreqMeasure());
            cStmt.setTimestamp(5, bpm.getTimeMeasured());
            cStmt.registerOutParameter(6,Types.INTEGER);
            cStmt.execute();
            return cStmt.getInt(6)==0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BloodPressureMeasureManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
