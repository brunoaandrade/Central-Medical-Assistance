/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.sqlcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago Silva
 * Singleton BDPathCon
 */

public class BDPathCon {
    private Connection con;
    private final static String driverName="com.mysql.jdbc.Driver";
    private static BDPathCon bdCon = null;
    
    private BDPathCon(){
        try{
            //Loading Driver for MySql
            Class.forName(driverName);
            con = DriverManager.getConnection("jdbc:mysql://192.168.160.84:3306/mapsDB", "root", "root");
        }catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        } catch (SQLException ex) {
            Logger.getLogger(BDPathCon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static BDPathCon getBDPathCon(){
        if(bdCon!=null){
            return bdCon;
        }
        else{
            return bdCon = new BDPathCon();
        }
        
    }
    
    public Connection getCon(){
        return con;    
    }
    
}
