/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.maps.commonClasses;

/**
 *
 * @author Tiago
 */
public class AndroidStuff {
    
    private PAction pAction;
    private TemperatureMeasure temperatureMeasure;
    private BloodPressureMeasure bloodPressureMeasure;
    private AndroidStuffType androidStuffType;
    private DrugTakeRegister drugTakeRegister;
    
    public AndroidStuff() {
    }

    public AndroidStuff(TemperatureMeasure temperatureMeasure) {
        this.temperatureMeasure = temperatureMeasure;
        androidStuffType = AndroidStuffType.Temperature;
    }

    public AndroidStuff(BloodPressureMeasure bloodPressureMeasure) {
        this.bloodPressureMeasure = bloodPressureMeasure;
        androidStuffType = AndroidStuffType.Blood;
    }

    public AndroidStuff(PAction pAction) {
        this.pAction = pAction;
        androidStuffType = AndroidStuffType.Action;
    }

    public AndroidStuff(DrugTakeRegister drugTakeRegister) {
        this.drugTakeRegister = drugTakeRegister;
        androidStuffType = AndroidStuffType.DrugTake;
    }

        
    public PAction getpAction() {
        return pAction;
    }

    public void setpAction(PAction pAction) {
        this.pAction = pAction;
    }

    public TemperatureMeasure getTemperatureMeasure() {
        return temperatureMeasure;
    }

    public void setTemperatureMeasure(TemperatureMeasure temperatureMeasure) {
        this.temperatureMeasure = temperatureMeasure;
    }

    public BloodPressureMeasure getBloodPressureMeasure() {
        return bloodPressureMeasure;
    }

    public void setBloodPressureMeasure(BloodPressureMeasure bloodPressureMeasure) {
        this.bloodPressureMeasure = bloodPressureMeasure;
    }

    public AndroidStuffType getAndroidStuffType() {
        return androidStuffType;
    }

    public void setAndroidStuffType(AndroidStuffType androidStuffType) {
        this.androidStuffType = androidStuffType;
    }

    public DrugTakeRegister getDrugTakeRegister() {
        return drugTakeRegister;
    }

    public void setDrugTakeRegister(DrugTakeRegister drugTakeRegister) {
        this.drugTakeRegister = drugTakeRegister;
    }

}
