/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.*;
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
 * @author ubuntu
 */
public class PatientTokenManager {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    /**
     * 
     * @param patient
     * @return
     * null - Patient argument is invalid
     */
    public static PatientToken getToken(Patient patient){
        if(patient == null){
            return null;
        }
        if(patient.getIdPatient()<=0){
            return null;
        }
        
        PatientToken token = new PatientToken(patient.getIdPatient());
        
        //METER NUMA FUNÇÃO MAIS TARDE!!
        //REFARCTOR TokenManager getPatieng to getNewToken
        if(!hasPatientToken(patient)){
            try {
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.addPatientToken);
            cStmt.setInt(1, patient.getIdPatient());
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

        CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.gettPatientToken);
        cStmt.setInt(1, patient.getIdPatient());
        cStmt.registerOutParameter(2, Types.VARCHAR);
        //cStmt.registerOutParameter(3,Types.INTEGER);
        cStmt.execute();
        token.setToken(cStmt.getString(2));
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return null;
        }
            
        return token;
    }
    
    private static boolean hasPatientToken(Patient patient){
        try {
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.hasPatientToken);
            cStmt.setInt(1, patient.getSns());
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
