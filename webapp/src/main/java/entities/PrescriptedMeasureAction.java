/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author Tiago
 */
public class PrescriptedMeasureAction implements Serializable {

    private PatientAction patientAction;
    private PrescriptedAction prescriptedAction;
    private MeasurePrescript measurePrescript;

    public PrescriptedMeasureAction() {
    }

    public PrescriptedMeasureAction(PatientAction patientAction, PrescriptedAction prescriptedAction, MeasurePrescript measurePrescript) {
        this.patientAction = patientAction;
        this.prescriptedAction = prescriptedAction;
        this.measurePrescript = measurePrescript;
    }

    public PatientAction getPatientAction() {
        return patientAction;
    }

    public void setPatientAction(PatientAction patientAction) {
        this.patientAction = patientAction;
    }

    public PrescriptedAction getPrescriptedAction() {
        return prescriptedAction;
    }

    public void setPrescriptedAction(PrescriptedAction prescriptedAction) {
        this.prescriptedAction = prescriptedAction;
    }

    public MeasurePrescript getMeasurePrescript() {
        return measurePrescript;
    }

    public void setMeasurePrescript(MeasurePrescript measurePrescript) {
        this.measurePrescript = measurePrescript;
    }

    @Override
    public String toString() {
        return "PrescriptedMeasureAction{" + "patientAction=" + patientAction + ", prescriptedAction=" + prescriptedAction + ", measurePrescript=" + measurePrescript + '}';
    }

}
