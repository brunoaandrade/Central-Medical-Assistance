/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.maps.commonClasses;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ubuntu
 */
public class HealthCare implements Serializable{
    private int idHeathCare;
    private int medicalNumber;
    private String mail;
    private String passwordHash;
    private String fname;
    private String lname;
    private Timestamp addedDate;
    
    public HealthCare(){
        //Do nothing
    }

    public HealthCare(int idHeathCare) {
        this.idHeathCare = idHeathCare;
    }

    public HealthCare(String mail, String passwordHash) {
        this.mail = mail;
        this.passwordHash = passwordHash;
    }
    

    public HealthCare(int medicalNumber, String mail, String passwordHash, String fname, String lname) {
        this.medicalNumber = medicalNumber;
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.fname = fname;
        this.lname = lname;
        this.addedDate = addedDate;
    }

    public HealthCare(int idHeathCare, int medicalNumber, String mail, String passwordHash, String fname, String lname, Timestamp addedDate) {
        this.idHeathCare = idHeathCare;
        this.medicalNumber = medicalNumber;
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.fname = fname;
        this.lname = lname;
        this.addedDate = addedDate;
    }

    
    public int getIdHeathCare() {
        return idHeathCare;
    }

    public void setIdHeathCare(int idHeathCare) {
        this.idHeathCare = idHeathCare;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }

    public int getMedicalNumber() {
        return medicalNumber;
    }

    public void setMedicalNumber(int medicalNumber) {
        this.medicalNumber = medicalNumber;
    }

    @Override
    public String toString() {
        return "HealthCare{" + "idHeathCare=" + idHeathCare + ", medicalNumber=" + medicalNumber + ", mail=" + mail + ", passwordHash=" + passwordHash + ", fname=" + fname + ", lname=" + lname + ", addedDate=" + addedDate + '}';
    }
    
    
    
}
