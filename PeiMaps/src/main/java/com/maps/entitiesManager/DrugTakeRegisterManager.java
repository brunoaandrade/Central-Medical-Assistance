/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.DrugTakeRegister;
import com.maps.entities.Patient;
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
public class DrugTakeRegisterManager {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    
    public static boolean addDrugTakeRegisterManager(DrugTakeRegister dtr){
        try {
            if(dtr==null){
                return false;
            }
            
            System.out.println("\n"+dtr);
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.insertDrugRegister);
            cStmt.setInt(1, dtr.getIdTake());
            cStmt.setTimestamp(2, dtr.getTimeTaked());
            cStmt.registerOutParameter(3,Types.INTEGER);
            cStmt.execute();
            System.out.println("\nO VALOR SQL: "+cStmt.getInt(3));
            return cStmt.getInt(3)==0;
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return false;
        }
    }
    
}
