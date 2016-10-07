/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;

/**
 *
 * @author Tiago
 */
public class DrugTakePrescripted {

    private int idTake;
    private int idAction;
    private Timestamp takeTime;
    private String drugName;
    private String description;

    public DrugTakePrescripted() {
    }

    public DrugTakePrescripted(int idTake, int idAction, Timestamp takeTime, String drugName, String description) {
        this.idTake = idTake;
        this.idAction = idAction;
        this.takeTime = takeTime;
        this.drugName = drugName;
        this.description = description;
    }

    public int getIdTake() {
        return idTake;
    }

    public void setIdTake(int idTake) {
        this.idTake = idTake;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public Timestamp getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Timestamp takeTime) {
        this.takeTime = takeTime;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DrugTakePrescripted{" + "idTake=" + idTake + ", idAction=" + idAction + ", takeTime=" + takeTime + ", drugName=" + drugName + ", description=" + description + '}';
    }

}
