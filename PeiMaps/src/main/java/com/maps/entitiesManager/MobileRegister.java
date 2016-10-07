/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entitiesManager;

import com.maps.entities.Activity;
import com.maps.entities.Patient;
import com.maps.sqlcon.BDPathCon;
import com.maps.sqlcon.SPCall;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class MobileRegister {
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    public static int addMobile(String mobileId, int idPatient){
        try {
            if(mobileId.isEmpty()){
                return -1;
            }
            //deviceMobRegistry (in marca varchar(15), in modelo varchar(15),in firmware varchar (20),tokendevices varchar(40), out result int)
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.deviceMobRegistry);
                        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB\n"
                    + "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            cStmt.setInt(1, idPatient);
            cStmt.setString(2, "Estatico");
            cStmt.setString(3, "Estatico");
            cStmt.setString(4, "Estatico");
            cStmt.setString(5, mobileId);
            
            cStmt.registerOutParameter(6,Types.INTEGER);
            cStmt.execute();
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB\n"
                    + "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"
                    + cStmt.getInt(6));
            return cStmt.getInt(6);
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return -3;
        }
    }
    
    
    public static boolean checkMobile(String mobileId, int idPatient){
        try {
            if(mobileId.isEmpty()){
                return false;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.hastokendevice);
            
            
            cStmt.setString(1, mobileId);
            cStmt.setInt(2, idPatient);
            cStmt.registerOutParameter(3,Types.INTEGER);
            
            
            cStmt.execute();
            
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n"
                    + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                    + "RESULT: "
                    + cStmt.getInt(3));
            return cStmt.getInt(3)==-1;
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
            //ERRO SQL
            return false;
        }
    }
    
    
    
    

    public static List<String> getList(int patient_id) {
        try {
            List<String> mobileList = new ArrayList<>();
            
            CallableStatement cStmt = myCon.getCon().prepareCall(SPCall.listDevicespatient);
            
            cStmt.setInt(1, patient_id);
            
            cStmt.registerOutParameter(2,Types.INTEGER);
            ResultSet rs = cStmt.executeQuery();
            
            while (rs.next())
            {
                mobileList.add(rs.getString("tokendevice"));
              
            }
            
            return mobileList;
        } catch (SQLException ex) {
            Logger.getLogger(MobileRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
