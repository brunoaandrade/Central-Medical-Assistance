/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.logger;

import com.mycompany.sqlcon.BDPathCon;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.internal.libraries.asm.Type;

/**
 *
 * @author Tiago Silva
 * 
 * 
 */
public class ManageUser {
    //Used to hash the PW
    private static final String hash = "f8f140c5";
    //Used on querry as security paramter, this value must be the same on the StoredProcedure handling the query 
    private static final String managerID = "006fc89d";
    //Used to hash
    private static MessageDigest md;
    //Prepare BD Con
    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    private PreparedStatement pS;
    
    //Checks if user exists
    private static final String checkUser = "SELECT FROM ";
    
    public ManageUser(){
        //nothing to do here
    }
    
    /**
     * Add's New user to the BD
     * 
     * @param user
     * @param pw
     * @return
     * 0    -   User Created With Success
     * 1    -   User Not Added, Error
     * 2    -   User already created
     * -1   -   Error SQL Exception
     * -2   -   Hash Error on Creation
     **/
    public int addUser(int user, String pw){
        
        try {
            
            byte[] hashed_pw = hashPW(pw);
            
            if(hashed_pw == null){
                return -2;
            }
            
            CallableStatement cStmt = myCon.getCon().prepareCall("{call addUserPatient(?, ?)}");
            cStmt.registerOutParameter(3,Types.INTEGER);
            cStmt.setInt(1, user);
            cStmt.setBytes(2, hashed_pw);
            ResultSet rs = cStmt.executeQuery();
            int outputValue = cStmt.getInt(3);
            //success - returns 0
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    
    /**
     * 
     * @param user
     * @param old_pw
     * @param new_pw
     * @return 
     * 0    -   User PassWord changed with success
     * 1    -   User and/or OldPassWord not found
     * 2    -   Other Error
     */
    public int changeUserPw(String user, String old_pw, String new_pw){
        
        //Hash old_pw   #err - return 2
        
        //Get_Query and compare #err - return 1
        
        //Hash new_pw, call StoredProcedure with arguments: @param managerID , @param user and @param hashed(new_pw)
        
       return 0; 
    }
    
    private byte[] hashPW(String pw){
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] hashed_pw = md.digest((pw+hash).getBytes());
            return hashed_pw;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, "hashPW on ManageUser.java not working!", ex);
            return null;
        }
    }
    
}
