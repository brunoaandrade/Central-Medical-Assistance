package com.app.maps.commonClasses;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Timestamp;


public class TemperatureMeasure implements Serializable{
    private int idTMeasure;
    private int idMeasure;
    private int idMeasurepresvript;
    private int tMeasure;
    private Timestamp timeMeasured;

    public TemperatureMeasure() {
    }

    public TemperatureMeasure(int idTMeasure, int idMeasure, int idMeasurepresvript, int tMeasure) {
        this.idTMeasure = idTMeasure;
        this.idMeasure = idMeasure;
        this.idMeasurepresvript = idMeasurepresvript;
        this.tMeasure = tMeasure;
    }

    public TemperatureMeasure(int idTMeasure, int idMeasure, int idMeasurepresvript, int tMeasure, Timestamp timeMeasured) {
        this.idTMeasure = idTMeasure;
        this.idMeasure = idMeasure;
        this.idMeasurepresvript = idMeasurepresvript;
        this.tMeasure = tMeasure;
        this.timeMeasured = timeMeasured;
    }

    public TemperatureMeasure(int idMeasure, int idMeasurepresvript, int tMeasure, Timestamp timeMeasured) {
        this.idMeasure = idMeasure;
        this.idMeasurepresvript = idMeasurepresvript;
        this.tMeasure = tMeasure;
        this.timeMeasured = timeMeasured;
    }
    

    public int getIdTMeasure() {
        return idTMeasure;
    }

    public void setIdTMeasure(int idTMeasure) {
        this.idTMeasure = idTMeasure;
    }

    public int getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(int idMeasure) {
        this.idMeasure = idMeasure;
    }

    public int getIdMeasurepresvript() {
        return idMeasurepresvript;
    }

    public void setIdMeasurepresvript(int idMeasurepresvript) {
        this.idMeasurepresvript = idMeasurepresvript;
    }

    public int gettMeasure() {
        return tMeasure;
    }

    public void settMeasure(int tMeasure) {
        this.tMeasure = tMeasure;
    }

    public Timestamp getTimeMeasured() {
        return timeMeasured;
    }

    public void setTimeMeasured(Timestamp timeMeasured) {
        this.timeMeasured = timeMeasured;
    }

    @Override
    public String toString() {
        return "TemperatureMeasure{" + "idTMeasure=" + idTMeasure + ", idMeasure=" + idMeasure + ", idMeasurepresvript=" + idMeasurepresvript + ", tMeasure=" + tMeasure + ", timeMeasured=" + timeMeasured + '}';
    }
    
    
}
