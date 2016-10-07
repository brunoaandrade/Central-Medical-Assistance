/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.entities;

import java.sql.Timestamp;

/**
 *
 * @author ubuntu
 */
public class HealthCarePro {
    private int idHeathCare;
    private String mail;
    private String passwordHash;
    private String fname;
    private String lname;
    private Timestamp addedDate;
    
    public HealthCarePro(){
        //Do nothing
    }

    public HealthCarePro(int idHeathCare) {
        this.idHeathCare = idHeathCare;
    }

    public HealthCarePro(String mail, String passwordHash, String fname, String lname) {
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.fname = fname;
        this.lname = lname;
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
    
    
    
}
