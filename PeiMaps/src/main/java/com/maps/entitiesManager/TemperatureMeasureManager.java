/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.TemperatureMeasure;
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
public class TemperatureMeasureManager {

    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    public static boolean addPrescriptedMeasure(TemperatureMeasure tempM) {
            try {
                /*in idMeasure int(11), in tMeasure int(11),
    in timeMeasured timestamp, out result int*/
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.insertPrescrTempMeasure);
            cStmt.setInt(1, tempM.getIdMeasurepresvript());
            cStmt.setInt(2, tempM.gettMeasure());
            cStmt.setTimestamp(3, tempM.getTimeMeasured());
            cStmt.registerOutParameter(4,Types.INTEGER);
            cStmt.execute();
            return cStmt.getInt(4)==0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BloodPressureMeasureManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
    }
    
    public static boolean addFreeMeasure(TemperatureMeasure tempM, int patientId) {
            try {
                /*in idPatient int(11), in tMeasure int(11),
    in timeMeasured timestamp, out result int*/
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.insertFreeTempMeasure);
            cStmt.setInt(1, patientId);
            cStmt.setInt(2, tempM.gettMeasure());
            cStmt.setTimestamp(3, tempM.getTimeMeasured());
            cStmt.registerOutParameter(4,Types.INTEGER);
            cStmt.execute();
            return cStmt.getInt(4)==0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BloodPressureMeasureManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
    }
    
}
