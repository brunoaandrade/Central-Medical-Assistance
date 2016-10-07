/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.Patient;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ubuntu
 */
public class PatientManager {
    
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();

    public PatientManager(){
        //does nothing
    }
    
    /**
     * 
     * @param patient
     * @return 
     * Return Values:
     * 0    Patient added successful
     * -1   Patient argument is null
     * 
     */
    public static int addPatient(Patient patient){
        try {
            if(patient==null){
                return -1;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.addPatient);
            cStmt.setInt(1, patient.getSns());
            cStmt.setString(2, patient.getMail());
            cStmt.setString(3, patient.getPasswordHash());
            cStmt.setString(4, patient.getFname());
            cStmt.setString(5, patient.getLname());
            cStmt.registerOutParameter(6,Types.INTEGER);
            cStmt.execute();
            return cStmt.getInt(6);
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return -3;
        }
    }
    
    /**
     * 
     * @param patient
     * @return 
     * 
     */
    public static boolean isValidLogin(Patient patient){
        try {
            if(patient==null || patient.getMail().isEmpty() || patient.getPasswordHash().isEmpty()){
                return false;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.getPatientID);
            
            cStmt.setString(1, patient.getMail());
            cStmt.setString(2, patient.getPasswordHash());
            cStmt.registerOutParameter(3,Types.INTEGER);
            cStmt.execute();
            patient.setIdPatient(cStmt.getInt(3));   
            return patient.getIdPatient() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
        
        
        
    }
    
    public static Patient getPatientByToken(String token) {
        try {
            Patient patient = new Patient();
            
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.getPatientInfo);
            cStmt.setString(1, token);
            cStmt.registerOutParameter(2, Types.INTEGER); //_SNS
            
            //result set: 
            //sns, mail, fname, lname
            ResultSet rs = cStmt.executeQuery();
            
            if(cStmt.getInt(2)==0){
                while(rs.next()){
                    patient.setIdPatient(rs.getInt("idPatient"));
                    patient.setSns(rs.getInt("sns"));
                    patient.setMail(rs.getString("mail"));
                    patient.setFname(rs.getString("fname"));
                    patient.setLname(rs.getString("lname"));
                }
            }
            
            
            return patient;
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static int changePatientTextHistory(Patient patient) {
        
        try {
            if(patient==null){
                return -1;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.changeTextHistory);
            cStmt.setInt(1, patient.getIdPatient());
            cStmt.setString(2, patient.getHistory());
            cStmt.registerOutParameter(3,Types.INTEGER);
            cStmt.execute();
            
            return cStmt.getInt(3);
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return -3;
        }
    }
    
    
    
    public static int changeIdHealthCare(Patient patient) {
        
        try {
            if(patient==null){
                return -1;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.assocDoctoPatient);
            cStmt.setInt(2, patient.getIdPatient());
            cStmt.setInt(1, patient.getIdHealthCare().getIdHeathCare());
            cStmt.registerOutParameter(3,Types.INTEGER);
            cStmt.execute();
            
            return cStmt.getInt(3);
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return -3;
        }
    }
    
    public static int patientFavourite(Patient patient) {
        
        try {
            if(patient==null){
                return -1;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.patientFavourite);
            cStmt.setInt(1, patient.getIdPatient());
            cStmt.registerOutParameter(2,Types.INTEGER);
            cStmt.execute();
            
            return cStmt.getInt(2);
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return -3;
        }
    }
}
