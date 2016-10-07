/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maps.entities;

import java.sql.Timestamp;

/**
 *
 * @author Tiago
 */
public class DrugTakeRegister {

    private int idTakeRegister;
    private int idTake;
    private Timestamp timeTaked;

    public DrugTakeRegister() {
    }

    public DrugTakeRegister(int idTakeRegister, int idTake, Timestamp timeTaked) {
        this.idTakeRegister = idTakeRegister;
        this.idTake = idTake;
        this.timeTaked = timeTaked;
    }

    public int getIdTakeRegister() {
        return idTakeRegister;
    }

    public void setIdTakeRegister(int idTakeRegister) {
        this.idTakeRegister = idTakeRegister;
    }

    public int getIdTake() {
        return idTake;
    }

    public void setIdTake(int idTake) {
        this.idTake = idTake;
    }

    public Timestamp getTimeTaked() {
        return timeTaked;
    }

    public void setTimeTaked(Timestamp timeTaked) {
        this.timeTaked = timeTaked;
    }

    @Override
    public String toString() {
        return "DrugTakeRegister{" + "idTakeRegister=" + idTakeRegister + ", idTake=" + idTake + ", timeTaked=" + timeTaked + '}';
    }

}
