/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entities;

import java.io.Serializable;

/**
 *
 * @author Tiago
 */
public class HealthCareToken implements Serializable{
    private int idHealthCare;
    private String token;

    public HealthCareToken() {
    }

    public HealthCareToken(int idHealthCare) {
        this.idHealthCare = idHealthCare;
    }

    public HealthCareToken(int idHealthCare, String token) {
        this.idHealthCare = idHealthCare;
        this.token = token;
    }

    public int getIdHealthCare() {
        return idHealthCare;
    }

    public void setIdHealthCare(int idHealthCare) {
        this.idHealthCare = idHealthCare;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
