/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.maps.sqlcon.BDPathCon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("registoprescricao")
public class RegistoPrescricaoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RegistoPrescricaoResource
     */
    public RegistoPrescricaoResource() {
    }


    /**
     * PUT method for updating or creating an instance of RegistoPrescricaoResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    @Produces("text/plain")
    public String putJson(String content) {
        int patientid=1;
        try {
            PreparedStatement p;
            
            p = BDPathCon.getBDPathCon().getCon().prepareStatement("INSERT INTO PatientAction(idPatient) VALUES "+patientid, Statement.RETURN_GENERATED_KEYS);
            p.executeUpdate();
            
            ResultSet rs = p.getGeneratedKeys();
            rs.next();
            int auto_id = rs.getInt(1);
             
            return "ID GERADO: "+auto_id;
            
        } catch (SQLException ex) {
            Logger.getLogger(RegistoPrescricaoResource.class.getName()).log(Level.SEVERE, null, ex);
            return "MEH ERRO";
        }
        
    }
}
