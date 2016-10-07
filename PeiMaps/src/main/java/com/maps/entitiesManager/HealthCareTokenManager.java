/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.HealthCare;
import com.maps.entities.HealthCareToken;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import com.maps.utilities.TokenManager;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class HealthCareTokenManager {

    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    /**
     * 
     * @param healthCare
     * @return
     * null - Patient argument is invalid
     */
    public static HealthCareToken getToken(HealthCare healthCare){
        if(healthCare == null){
            return null;
        }
        if(healthCare.getIdHeathCare()<=0){
            return null;
        }
        
        HealthCareToken token = new HealthCareToken(healthCare.getIdHeathCare());
        
        //METER NUMA FUNÇÃO MAIS TARDE!!
        //REFARCTOR TokenManager getPatieng to getNewToken
        if(!hasHealthCareToken(healthCare)){
            try {
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.addHealthCareToken);
            cStmt.setInt(1, healthCare.getIdHeathCare());
            cStmt.setString(2, TokenManager.getPatientToken());
            cStmt.registerOutParameter(3,Types.INTEGER);
            cStmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
                //ERRO SQL
                return null;
            }
            
        }
        
        //LATTER FUNÇAO NAO ESQUECER
        // GET TOKEN
        try {

        CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.getHealthCareToken);
        cStmt.setInt(1, healthCare.getIdHeathCare());
        cStmt.registerOutParameter(2, Types.VARCHAR);
        cStmt.registerOutParameter(3,Types.INTEGER);
        cStmt.execute();
        token.setToken(cStmt.getString(2));
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return null;
        }
            
        return token;
    }
    
    private static boolean hasHealthCareToken(HealthCare healthCare){
        try {
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.hasHealthCareToken);
            cStmt.setInt(1, healthCare.getIdHeathCare());
            cStmt.registerOutParameter(2,Types.BOOLEAN);
            cStmt.execute();
            return cStmt.getBoolean(2);
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return false;
        }
    }

    //Verifica se um dado token existe
    public static boolean checkToken(String token) {
        try {
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.existsPatientToken);
            cStmt.setString(1, token);
            cStmt.registerOutParameter(2,Types.BOOLEAN);
            cStmt.execute();
            return cStmt.getBoolean(2);
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return false;
        }
    }

    
}
