/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.HealthCare;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ubuntu
 */
public class HealthCareManager {
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    
    public static int addHealthCare(HealthCare healthcarepro){
        try {
            if(healthcarepro==null){
                return -1;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.addHealthCarePro);
            cStmt.setInt(1, healthcarepro.getMedicalNumber());
            cStmt.setString(2, healthcarepro.getMail());
            cStmt.setString(3, healthcarepro.getPasswordHash());
            cStmt.setString(4, healthcarepro.getFname());
            cStmt.setString(5, healthcarepro.getLname());
            cStmt.registerOutParameter(6,Types.INTEGER);
            cStmt.execute();
            return cStmt.getInt(6);
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return -3;
        }
    }

    public static boolean isValidLogin(HealthCare healthCare) {
        try {
            if(healthCare==null || healthCare.getMail().isEmpty() || healthCare.getPasswordHash().isEmpty()){
                return false;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.getHealthCareID);
            
            cStmt.setString(1, healthCare.getMail());
            cStmt.setString(2, healthCare.getPasswordHash());
            cStmt.registerOutParameter(3,Types.INTEGER);
            cStmt.execute();
            healthCare.setIdHeathCare(cStmt.getInt(3));   
            return healthCare.getIdHeathCare()> 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static HealthCare geHealthCaretByToken(String token) {
        try {
            HealthCare healthCare = new HealthCare();

            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.getHealthCareByToken);
            cStmt.setString(1, token);
            cStmt.registerOutParameter(2, Types.VARCHAR); //_MAIL
            cStmt.registerOutParameter(3, Types.VARCHAR); //_fName
            cStmt.registerOutParameter(4, Types.VARCHAR); //_lName
            cStmt.execute();
            healthCare.setMail(cStmt.getString(2));
            healthCare.setFname(cStmt.getString(3));
            healthCare.setLname(cStmt.getString(4));

            return healthCare;
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static int changeMailHealthCare(HealthCare healthCare) {
        
        try {
            if(healthCare==null){
                return -1;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.editDocMail);
            cStmt.setInt(1, healthCare.getIdHeathCare());
            cStmt.setString(2, healthCare.getMail());
            cStmt.registerOutParameter(3,Types.INTEGER);
            cStmt.execute();
            
            return cStmt.getInt(3);
        } catch (SQLException ex) {
            Logger.getLogger(HealthCareManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return -3;
        }
    } 

}
