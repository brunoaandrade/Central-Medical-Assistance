/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.maps.commonClasses;

import java.io.Serializable;

/**
 *
 * @author ubuntu
 */
public class PatientToken implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPatient;
    private String token;

    public PatientToken(){
        //Do Nothing
    }
    
    public PatientToken(int idPatient) {
        this.idPatient = idPatient;
    }

    public PatientToken(int idPatient, String token) {
        this.idPatient = idPatient;
        this.token = token;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
}
