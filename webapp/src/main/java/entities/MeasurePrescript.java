/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Tiago
 */
public class MeasurePrescript implements Serializable {

    private int idMeasure;
    private MeasureType measureType;
    private int idPrescriptact;
    private Timestamp timeMeasurePrescript;

    public MeasurePrescript() {
    }

    public MeasurePrescript(MeasureType measureType) {
        this.measureType = measureType;
    }

    public MeasurePrescript(int idMeasure, MeasureType measureType, int idPrescriptact, Timestamp timeMeasurePrescript) {
        this.idMeasure = idMeasure;
        this.measureType = measureType;
        this.idPrescriptact = idPrescriptact;
        this.timeMeasurePrescript = timeMeasurePrescript;
    }

    public MeasurePrescript(MeasureType measureType, int idPrescriptact, Timestamp timeMeasurePrescript) {
        this.measureType = measureType;
        this.idPrescriptact = idPrescriptact;
        this.timeMeasurePrescript = timeMeasurePrescript;
    }

    public MeasurePrescript(MeasureType measureType, int idPrescriptact) {
        this.measureType = measureType;
        this.idPrescriptact = idPrescriptact;
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

    public int getIdPrescriptact() {
        return idPrescriptact;
    }

    public void setIdPrescriptact(int idPrescriptact) {
        this.idPrescriptact = idPrescriptact;
    }

    public Timestamp getTimeMeasurePrescript() {
        return timeMeasurePrescript;
    }

    public void setTimeMeasurePrescript(Timestamp timeMeasurePrescript) {
        this.timeMeasurePrescript = timeMeasurePrescript;
    }

    @Override
    public String toString() {
        return "MeasurePrescript{" + "idMeasure=" + idMeasure + ", measureType=" + measureType + ", idPrescriptact=" + idPrescriptact + ", timeMeasurePrescript=" + timeMeasurePrescript + '}';
    }

}
