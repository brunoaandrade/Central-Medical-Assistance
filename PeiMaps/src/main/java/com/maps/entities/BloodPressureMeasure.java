/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Tiago
 */
public class BloodPressureMeasure implements Serializable{
    private int idBPMeasure;
    private int idMeasurefree;
    private int idMeasureprescript;
    private int dMeasure;
    private int sMeasure;
    private int freqMeasure;
    private Timestamp timeMeasured;

    public BloodPressureMeasure() {
    }

    public BloodPressureMeasure(int idBPMeasure, int idMeasurefree, int idMeasureprescript, int dMeasure, int sMeasure, int freqMeasure) {
        this.idBPMeasure = idBPMeasure;
        this.idMeasurefree = idMeasurefree;
        this.idMeasureprescript = idMeasureprescript;
        this.dMeasure = dMeasure;
        this.sMeasure = sMeasure;
        this.freqMeasure = freqMeasure;
    }

    public BloodPressureMeasure(int idBPMeasure, int idMeasurefree, int idMeasureprescript, int dMeasure, int sMeasure, int freqMeasure, Timestamp timeMeasured) {
        this.idBPMeasure = idBPMeasure;
        this.idMeasurefree = idMeasurefree;
        this.idMeasureprescript = idMeasureprescript;
        this.dMeasure = dMeasure;
        this.sMeasure = sMeasure;
        this.freqMeasure = freqMeasure;
        this.timeMeasured = timeMeasured;
    }

    public BloodPressureMeasure(int idMeasurefree, int idMeasureprescript, int dMeasure, int sMeasure, int freqMeasure, Timestamp timeMeasured) {
        this.idMeasurefree = idMeasurefree;
        this.idMeasureprescript = idMeasureprescript;
        this.dMeasure = dMeasure;
        this.sMeasure = sMeasure;
        this.freqMeasure = freqMeasure;
        this.timeMeasured = timeMeasured;
    }

    //ESTE
    public BloodPressureMeasure(int idMeasureprescript, int dMeasure, int sMeasure, int freqMeasure, Timestamp timeMeasured) {
        this.idMeasureprescript = idMeasureprescript;
        this.dMeasure = dMeasure;
        this.sMeasure = sMeasure;
        this.freqMeasure = freqMeasure;
        this.timeMeasured = timeMeasured;
    }

    
    
    public int getIdBPMeasure() {
        return idBPMeasure;
    }

    public void setIdBPMeasure(int idBPMeasure) {
        this.idBPMeasure = idBPMeasure;
    }

    public int getIdMeasurefree() {
        return idMeasurefree;
    }

    public void setIdMeasurefree(int idMeasurefree) {
        this.idMeasurefree = idMeasurefree;
    }

    public int getIdMeasureprescript() {
        return idMeasureprescript;
    }

    public void setIdMeasureprescript(int idMeasureprescript) {
        this.idMeasureprescript = idMeasureprescript;
    }

    public int getdMeasure() {
        return dMeasure;
    }

    public void setdMeasure(int dMeasure) {
        this.dMeasure = dMeasure;
    }

    public int getsMeasure() {
        return sMeasure;
    }

    public void setsMeasure(int sMeasure) {
        this.sMeasure = sMeasure;
    }

    public int getFreqMeasure() {
        return freqMeasure;
    }

    public void setFreqMeasure(int freqMeasure) {
        this.freqMeasure = freqMeasure;
    }

    public Timestamp getTimeMeasured() {
        return timeMeasured;
    }

    public void setTimeMeasured(Timestamp timeMeasured) {
        this.timeMeasured = timeMeasured;
    }

    @Override
    public String toString() {
        return "BloodPressureMeasure{" + "idBPMeasure=" + idBPMeasure + ", idMeasurefree=" + idMeasurefree + ", idMeasureprescript=" + idMeasureprescript + ", dMeasure=" + dMeasure + ", sMeasure=" + sMeasure + ", freqMeasure=" + freqMeasure + ", timeMeasured=" + timeMeasured + '}';
    }
    
    
    
}
