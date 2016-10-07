/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.utilities;

/**
 *
 * @author Tiago
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.Patient;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendMessage {


    

    public static <T> boolean sendMessage(T elem, int idPatient){
        
        try {
            String QUEUE_NAME = "patient_"+idPatient;
            Gson gson = new GsonBuilder().create();
            
            ConnectionFactory factory = new ConnectionFactory();
            
            factory.setUsername("maps");
            factory.setPassword("maps");
            
            factory.setHost("192.168.160.84");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = gson.toJson(elem);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            
            channel.close();
            connection.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
    }
    
    
    
    public static <T> boolean sendMessageMobile(T elem, int idPatient, String id_mobile){
        
        try {
            String QUEUE_NAME = "patient_"+idPatient+"_"+id_mobile;
            Gson gson = new GsonBuilder().create();
            
            ConnectionFactory factory = new ConnectionFactory();
            
            factory.setUsername("maps");
            factory.setPassword("maps");
            
            factory.setHost("192.168.160.84");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = gson.toJson(elem);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            
            channel.close();
            connection.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
    }
    
}