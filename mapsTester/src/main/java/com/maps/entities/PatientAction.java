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
public class PatientAction implements Serializable{
    private int idAction;
    private int idPatient;

    public PatientAction() {
    }

    public PatientAction(int idAction, int idPatient) {
        this.idAction = idAction;
        this.idPatient = idPatient;
    }

    public PatientAction(int idPatient) {
        this.idPatient = idPatient;
    }

    
    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    @Override
    public String toString() {
        return "PatientAction{" + "idAction=" + idAction + ", idPatient=" + idPatient + '}';
    }
    
    
}
