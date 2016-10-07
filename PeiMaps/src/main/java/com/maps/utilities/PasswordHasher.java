/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago Silva
 * A ALTERAR!!!!!!!!!!!!!! 
 * 
 */
public class PasswordHasher {
    //Used to hash the PW
    private static final String hash = "f8f140c5";
    //Used to hash
    private static MessageDigest md;
    
    
    public PasswordHasher(){
        //nothing to do here
    }
    
    
    private static byte[] hashPW(String pw){
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] hashed_pw = md.digest((pw+hash).getBytes());
            return hashed_pw;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordHasher.class.getName()).log(Level.SEVERE, "hashPW on ManageUser.java not working!", ex);
            return null;
        }
    }
    
    public static String hashedPW(String pw){
        return new String(hashPW(pw));
    }
    
    
}
