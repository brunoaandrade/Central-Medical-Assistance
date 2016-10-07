/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.maps.commonClasses;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author ubuntu
 */
public class Patient implements Serializable{
    
    private int idPatient=-1;
    private int sns;
    private String gender;
    private String mail;
    private String passwordHash;
    private String fname;
    private String lname;
    private HealthCare idHealthCare;
    private Timestamp addedDate;
    private String history;
    private Date borndate;
    private String city;
    private int favourite;
    
    
    public Patient(){
        //Do nothing
    }
    
    public Patient(int idPatient){
        this.idPatient=idPatient;
    }

    public Patient(int sns,String gender, String mail, String passwordHash, String fname, String lname) {
        this.sns = sns;
        this.gender = gender;
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.fname = fname;
        this.lname = lname;
    }

    public Patient(String mail, String passwordHash) {
        this.mail = mail;
        this.passwordHash = passwordHash;
    }


    public Patient(int idPatient, String gender, int sns, String mail, String passwordHash, String fname, String lname, HealthCare idHealthCare, Timestamp addedDate) {
        this.idPatient = idPatient;
        this.gender = gender;
        this.sns = sns;
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.fname = fname;
        this.lname = lname;
        this.idHealthCare = idHealthCare;
        this.addedDate = addedDate;
    }

    public Patient(int sns, String gender, String mail, String passwordHash, String fname, String lname, HealthCare idHealthCare, Timestamp addedDate, String history) {
        this.sns = sns;
        this.gender = gender;
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.fname = fname;
        this.lname = lname;
        this.idHealthCare = idHealthCare;
        this.addedDate = addedDate;
        this.history = history;
    }

    public Patient(int sns, String gender, String mail, String passwordHash, String fname, String lname, HealthCare idHealthCare, 
 Timestamp addedDate, String history, Date borndate,String city,int favourite) 
    {
        this.sns = sns;
        this.gender = gender;
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.fname = fname;
        this.lname = lname;
        this.idHealthCare = idHealthCare;
        this.addedDate = addedDate;
        this.history = history;
        this.borndate = borndate;
        this.city = city;
        this.favourite = favourite;
    }

    public void setBorndate(Date borndate) {
        this.borndate = borndate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public Date getBorndate() {
        return borndate;
    }

    public String getCity() {
        return city;
    }

    public int getFavourite() {
        return favourite;
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

    public HealthCare getIdHealthCare() {
        return idHealthCare;
    }

    public void setIdHealthCare(HealthCare idHealthCare) {
        this.idHealthCare = idHealthCare;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "Patient{" + "idPatient=" + idPatient + ", sns=" + sns + ", gender=" + gender + ", mail=" + mail + ", passwordHash=" + passwordHash + ", fname=" + fname + ", lname=" + lname + ", idHealthCare=" + idHealthCare + ", addedDate=" + addedDate + ", history=" + history + '}';
    }


    
}
