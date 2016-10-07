/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.HealthCare;
import com.maps.entities.Patient;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class SendValues {
    
    public static boolean enviaCoisas(HealthCare healthCare){
        
        StringBuilder output = new StringBuilder();
        String bodyParameters;
        
        try {
                    
            
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(healthCare);
            
            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL("http://192.168.160.84:8080/PeiMaps/webresources/healthcareregister");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            
            
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST"); 
            urlConnection.setRequestProperty("Content-Type", "application/json"); 
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream ());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();
                
                /* obtém a resposta do pedido */
                int n = 1;	
                while (n > 0) {
	
                    byte[] b = new byte[4096];

                    n =  is.read(b);
	
                    if (n > 0)
                        output.append(new String(b, 0, n));
                }
                
                System.out.println(output.toString());
                
            } 
            else {
                System.out.println(urlConnection.getResponseCode());
            }

            urlConnection.disconnect();
        }
        catch (IOException ex) {
            
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    
}
