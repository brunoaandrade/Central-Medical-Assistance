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
public class Patient {
    
    private int idPatient;
    private int sns;
    private String mail;
    private String gender;
    private String passwordHash;
    private String fname;
    private String lname;
    private HealthCarePro idHealthCare;
    private Timestamp addedDate;
    
    public Patient(){
        //Do nothing
    }
    
    public Patient(int idPatient){
        this.idPatient=idPatient;
    }

    public Patient(int sns, String mail, String gender, String passwordHash, String fname, String lname) {
        this.sns = sns;
        this.mail = mail;
        this.gender = gender;
        this.passwordHash = passwordHash;
        this.fname = fname;
        this.lname = lname;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getSns() {
        return sns;
    }

    public void setSns(int sns) {
        this.sns = sns;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public HealthCarePro getIdHealthCare() {
        return idHealthCare;
    }

    public void setIdHealthCare(HealthCarePro idHealthCare) {
        this.idHealthCare = idHealthCare;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }
    
    
}
