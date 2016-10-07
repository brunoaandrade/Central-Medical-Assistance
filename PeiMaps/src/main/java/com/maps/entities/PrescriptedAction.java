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
public class PrescriptedAction implements Serializable{
    private int idPrescriptact;
    private int idAction;
    private int idHealthCare;
    private Timestamp timeToStart;
    private ActionType actiontype;
    private int period;
    private int numberreps;

    public PrescriptedAction() {
    }

    public PrescriptedAction(int idPrescriptact, int idAction, int idHealthCare, Timestamp timeToStart, ActionType actiontype, int period, int numberreps) {
        this.idPrescriptact = idPrescriptact;
        this.idAction = idAction;
        this.idHealthCare = idHealthCare;
        this.timeToStart = timeToStart;
        this.actiontype = actiontype;
        this.period = period;
        this.numberreps = numberreps;
    }

    public PrescriptedAction(int idAction, int idHealthCare, Timestamp timeToStart, ActionType actiontype, int period, int numberreps) {
        this.idAction = idAction;
        this.idHealthCare = idHealthCare;
        this.timeToStart = timeToStart;
        this.actiontype = actiontype;
        this.period = period;
        this.numberreps = numberreps;
    }

    public PrescriptedAction(Timestamp timeToStart, ActionType actiontype, int period, int numberreps) {
        this.timeToStart = timeToStart;
        this.actiontype = actiontype;
        this.period = period;
        this.numberreps = numberreps;
    }

    public PrescriptedAction(int idHealthCare, Timestamp timeToStart, ActionType actiontype, int period, int numberreps) {
        this.idHealthCare = idHealthCare;
        this.timeToStart = timeToStart;
        this.actiontype = actiontype;
        this.period = period;
        this.numberreps = numberreps;
    }

    
    
    public int getIdPrescriptact() {
        return idPrescriptact;
    }

    public void setIdPrescriptact(int idPrescriptact) {
        this.idPrescriptact = idPrescriptact;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public int getIdHealthCare() {
        return idHealthCare;
    }

    public void setIdHealthCare(int idHealthCare) {
        this.idHealthCare = idHealthCare;
    }

    public Timestamp getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(Timestamp timeToStart) {
        this.timeToStart = timeToStart;
    }

    public ActionType getActiontype() {
        return actiontype;
    }

    public void setActiontype(ActionType actiontype) {
        this.actiontype = actiontype;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getNumberreps() {
        return numberreps;
    }

    public void setNumberreps(int numberreps) {
        this.numberreps = numberreps;
    }

    @Override
    public String toString() {
        return "PrescriptedAction{" + "idPrescriptact=" + idPrescriptact + ", idAction=" + idAction + ", idHealthCare=" + idHealthCare + ", timeToStart=" + timeToStart + ", actiontype=" + actiontype + ", period=" + period + ", numberreps=" + numberreps + '}';
    }
    
    
    
}
