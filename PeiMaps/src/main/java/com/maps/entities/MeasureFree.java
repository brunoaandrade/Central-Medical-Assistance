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
public class MeasureFree implements Serializable{
    private int idMeasure;
    private MeasureType measureType;
    private int idFreeact;

    public MeasureFree() {
    }

    public MeasureFree(int idMeasure, MeasureType measureType, int idFreeact) {
        this.idMeasure = idMeasure;
        this.measureType = measureType;
        this.idFreeact = idFreeact;
    }

    public MeasureFree(MeasureType measureType, int idFreeact) {
        this.measureType = measureType;
        this.idFreeact = idFreeact;
    }

    
    public int getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(int idMeasure) {
        this.idMeasure = idMeasure;
    }

    public MeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }

    public int getIdFreeact() {
        return idFreeact;
    }

    public void setIdFreeact(int idFreeact) {
        this.idFreeact = idFreeact;
    }

    @Override
    public String toString() {
        return "MeasureFree{" + "idMeasure=" + idMeasure + ", measureType=" + measureType + ", idFreeact=" + idFreeact + '}';
    }
    
    
}
