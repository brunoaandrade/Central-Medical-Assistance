/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.Patient;
import com.maps.sqlcon.BDPathCon;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Tiago
 */
@Path("getPatientList")
public class GetPatientListResource {

    private final static BDPathCon myCon = BDPathCon.getBDPathCon();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetPatientListResource
     */
    public GetPatientListResource() {
    }

    /**
     * Retrieves representation of an instance of com.maps.services.GetPatientListResource
     * @param req
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("id") int req) {
        
        List<Patient> patientList = new ArrayList<>();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            Statement cStmt = myCon.getCon().createStatement();
            ResultSet rs = cStmt.executeQuery("SELECT * FROM Patient WHERE Patient.idHealthCare="+req);
            
            while (rs.next())
            {
                Patient patient = new Patient();
                int id = rs.getInt("idPatient");
                int sns = rs.getInt("sns");
                String gender = rs.getString("gender");
                String mail = rs.getString("mail");
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                String history = rs.getString("history");
                Date borndate = rs.getDate("borndate");
                Timestamp dateadded = rs.getTimestamp("addedDate");
                String city = rs.getString("city");
                int favourite = rs.getInt("favourite");

                patient.setIdPatient(id);
                patient.setSns(sns);
                patient.setMail(mail);
                patient.setFname(fName);
                patient.setLname(lName);
                patient.setGender(gender);
                patient.setHistory(history);
                patient.setBorndate(borndate);
                patient.setAddedDate(dateadded);
                patient.setCity(city);
                patient.setFavourite(favourite);
                patientList.add(patient);
              
            }
            
            return gson.toJson(patientList);
            
        } catch (SQLException ex) {
            Logger.getLogger(GetPatientListResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO return proper representation object
        return null;
    }

    /**
     * PUT method for updating or creating an instance of GetPatientListResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public String  putJson(String content) {
        
        //LATTER USE
        
        return null;
    }

    static class GetPatientListResource_JerseyClient {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://192.168.160.84:8080/PeiMaps/webresources";

        public GetPatientListResource_JerseyClient() {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("getPatientList");
        }

        public String putJson(Object requestEntity) throws ClientErrorException {
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
        }

        public String getJson(String id) throws ClientErrorException {
            WebTarget resource = webTarget;
            if (id != null) {
                resource = resource.queryParam("id", id);
            }
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
        }

        public void close() {
            client.close();
        }
    }
}
