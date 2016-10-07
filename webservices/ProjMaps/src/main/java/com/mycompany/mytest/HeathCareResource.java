/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.mytest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * REST Web Service
 *
 * @author MoLt1eS
 */
@Path("HeathCare")
public class HeathCareResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HeathCareResource
     */
    public HeathCareResource() {
    }

//    @GET
//    @Produces("text/plain")
//    public String getJson() {
//        return "HELL YEAH";
//    }
    /**
     * Retrieves representation of an instance of com.mycompany.mytest.HeathCareResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        return "Teste a funcionar V2!";
    }

    /**
     * PUT method for updating or creating an instance of HeathCareResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    private static ObjectMapper mapper = new ObjectMapper();
    
    @POST
    @Path("/inserir")
    @Consumes("application/json")
    public String putJson(String content) {
        
        Map<String, Object> json;
        Connection con=null;
        PreparedStatement p;
        
         String driverName="com.mysql.jdbc.Driver";

         
         try{
                //Loading Driver for MySql
                Class.forName(driverName);
            }catch (ClassNotFoundException e) {
                System.out.println(e.toString());
            }

        try {
            json = mapper.readValue(content, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            return "Erro json";
        }
            
       
        
        String n_credential = (String) json.get("n_credential");
        String name = (String) json.get("name");
        
        if(n_credential==null || name==null)
            return "Dados invalidos";

        try{
           con=DriverManager.getConnection("jdbc:mysql://192.168.160.84:3306/mapsDB", "root", "root");

            
            java.util.Date d = new java.util.Date();
            String q1 = "INSERT INTO healthCarePro(n_credential, name) VALUES ('" + n_credential + "','" + name+ "')";
                        
            p = con.prepareStatement(q1);          
            p.executeUpdate();
            con.close();
            
        }catch(SQLException e){
            return "Falha MySQL";
        }
        
        return "Worked!";
    }
    
    
    @POST
    @Path("/inserirPA")
    @Consumes("application/json")
    public String insertBloodPressure(String content) {
        
        Map<String, Object> json;
        Connection con=null;
        PreparedStatement p;
        
        Statement stmt;
        ResultSet rs = null;
        
        String driverName="com.mysql.jdbc.Driver";
        
        try{
            //Loading Driver for MySql
            Class.forName(driverName);
        }catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        try {
            json = mapper.readValue(content, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return "Erro json";
        }
        
        String patientID = (String) json.get("patient_id");
        String date = (String) json.get("date");
        String hour = (String) json.get("hour"); //MUDAR
        String measureType = (String) json.get("measure_type");
        //measureType for bloodpressure = bp
        String valueS = (String) json.get("valueSystolic");
        String valueD = (String) json.get("valueDiastolic");
        String freq = (String) json.get("cardiac_freq");
        
        if(patientID==null || date==null || hour==null || measureType==null || valueS==null || valueD==null || freq==null)
            return "Dados invalidos";

        try{
            con=DriverManager.getConnection("jdbc:mysql://192.168.160.84:3306/mapsDB", "root", "root");
            
            System.out.println("VAL 1");
            
            String q1 = "INSERT INTO healthCareRecord (patient_id, date, hour, measure_type) VALUES ('"+patientID + "','" +date+"','"+hour+"','"+measureType+"')";
            p = con.prepareStatement(q1);          
            p.executeUpdate();
            
            System.out.println("VAL 2");
            
            String temp = "SELECT MAX(hcr.id) as maxID FROM healthCareRecord as hcr";
            
            stmt = con.createStatement();          
            rs = stmt.executeQuery(temp);
            String res = null;
            while (rs.next()) {
                res = rs.getString("maxID");
           
            }
            stmt.close();
            System.out.println("VAL 3");
            
//            for (int counter = 0; rs.next(); counter++)
//                 res = rs.getString("maxID");
            
            System.out.println("VAL 4 temp="+res);
            
            String q2 = "INSERT INTO bloodPressureRecord VALUES ('"+res+"','"+valueS + "','" +valueD+"','"+freq+"')";         
            p = con.prepareStatement(q2);          
            p.executeUpdate();
            
            con.close(); 
            
        }catch(SQLException e){
            return "Falha MySQL";
        }
        
        return "Worked!";
    }
    
    @GET
    @Path("/inserirPA")
    @Produces("application/json")
    public String getBloodPressure(String content) {
        try {
            Connection con=null;
            Statement stmt;
            ResultSet rs = null;
        
            String vS = null;
            String vD = null;
            String f = null;
            
            String driverName="com.mysql.jdbc.Driver";
            String temp = "SELECT * FROM bloodPressureRecord ORDER BY healthCareRecord_id DESC LIMIT 1";
            con=DriverManager.getConnection("jdbc:mysql://192.168.160.84:3306/mapsDB", "root", "root");
            stmt = con.createStatement();          
            rs = stmt.executeQuery(temp);
            
            while (rs.next()) {
                vS = rs.getString(2);
                vD = rs.getString(3);
                f = rs.getString(4);
            }
            stmt.close();
            
            
            return "[{\"valueS\":"+vS+", \"valueD\":"+vD+", \"freq\":"+f+"}]";
        } catch (SQLException ex) {
            Logger.getLogger(HeathCareResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "{\"valueS\":\"100\", \"valueD\":\"80\", \"freq\":\"20\"}";
    }
//    public void connect(){
//
//        try{
//	
//           con=DriverManager.getConnection("jdbc:mysql://192.168.160.84:3306/mapsDB", "root", "root");
//            
//	
//            String q1 = "INSERT INTO healthCarePro(n_credential, name) VALUES ('2503','Tiago')";
//	
//	
//            p = con.prepareStatement(q1);          
//	
//            p.executeUpdate();
//	
//            con.close();
//	
//            
//	
//        }catch(Exception e){
//	
//            
//	
//          
//	
//        }
}
