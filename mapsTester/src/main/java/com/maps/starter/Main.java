/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.starter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maps.entities.HealthCare;
import com.maps.entities.Patient;
import com.maps.utilities.SendValues;

/**
 *
 * @author Tiago
 */
public class Main {
    public static void main(String[] args){
        Gson gson = new GsonBuilder().create();
        
        //GERADOR NOMES, MAIL, NUMEROS
        
        //
        
        
//      List<Patient> patientList = new ArrayList<Patient>();
        Patient patient = new Patient(233220407,"M", "miguel@ua.pt", "miguel", "Miguel", "Silva");
        System.out.println(gson.toJson(patient));
        
        HealthCare healthcare = new HealthCare(4924,"tj@ua.pt","tiago","Tiago","Joao");
        System.out.println(gson.toJson(healthcare));
//      patientList.add(patient);
        SendValues.enviaCoisas(healthcare);
        
    }
}
