/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subClasses;

import entities.ActionType;
import java.sql.Timestamp;

/**
 *
 * @author Bruno
 */
public class Tomas {

    int id;
    String Name;
    Timestamp Date;
    int tempMeasure;
    int fMeasure;
    int sMeasure;
    int dMeasure;
    ActionType at;

    public ActionType getAt() {
        return at;
    }

    public void setAt(ActionType at) {
        this.at = at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Timestamp getDate() {
        return Date;
    }

    public void setDate(Timestamp Date) {
        this.Date = Date;
    }

    public int getTempMeasure() {
        return tempMeasure;
    }

    public void setTempMeasure(int tempMeasure) {
        this.tempMeasure = tempMeasure;
    }

    public int getfMeasure() {
        return fMeasure;
    }

    public void setfMeasure(int fMeasure) {
        this.fMeasure = fMeasure;
    }

    public int getsMeasure() {
        return sMeasure;
    }

    public void setsMeasure(int sMeasure) {
        this.sMeasure = sMeasure;
    }

    public int getdMeasure() {
        return dMeasure;
    }

    public void setdMeasure(int dMeasure) {
        this.dMeasure = dMeasure;
    }

}
