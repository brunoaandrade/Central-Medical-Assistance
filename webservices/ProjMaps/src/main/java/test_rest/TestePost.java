/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test_rest;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MoLt1eS
 */
public class TestePost {
    
    public static void main(String[] args){
        URL url = null;
        
        try {
            //url = new URL("http://192.168.160.84:8080/ProjMaps/webresources/HeathCare/inserir");
            url = new URL("http://192.168.160.84:8080/ProjMaps/webresources/HeathCare/inserirPA");
        } catch (MalformedURLException ex) {
            System.out.println("Url mal inserida");
        }
        System.out.println("URL existente!");
        String toSend = null;               //Editar para json string a enviar
        
        //toSend="{\"n_credential\":\"63623\",\"name\":\"Jonono\"}";
        toSend="{\"patient_id\":\"1\", \"valueSystolic\":\"112\", \"valueDiastolic\":\"82\", \"cardiac_freq\":\"22\", \"hour\":\"12:00:40\", \"date\":\"2102-10-20\", \"measure_type\":\"bp\"}";    
        testPost(toSend, url);
    }
    
   
    public static void testPost(String toSend, URL url){


        try{
        
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());


        out.write(toSend);
        out.close();
        httpCon.getInputStream();
        System.out.println(toSend); 
        }
        catch(IOException ex){
            System.out.println("Algo correu mal!");
        }
    }
    
}
