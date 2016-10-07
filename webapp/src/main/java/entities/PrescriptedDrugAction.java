/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Tiago
 */
public class PrescriptedDrugAction {

    private PatientAction patientAction;
    private PrescriptedAction prescriptedAction;
    private DrugTakePrescripted drugTakePrescripted;

    public PrescriptedDrugAction() {
    }

    @Override
    public String toString() {
        return "PrescriptedDrugAction{" + "patientAction=" + patientAction + ", prescriptedAction=" + prescriptedAction + ", drugTakePrescripted=" + drugTakePrescripted + '}';
    }

    public PrescriptedDrugAction(PatientAction patientAction, PrescriptedAction prescriptedAction, DrugTakePrescripted drugTakePrescripted) {
        this.patientAction = patientAction;
        this.prescriptedAction = prescriptedAction;
        this.drugTakePrescripted = drugTakePrescripted;
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

    public DrugTakePrescripted getDrugTakePrescripted() {
        return drugTakePrescripted;
    }

    public void setDrugTakePrescripted(DrugTakePrescripted drugTakePrescripted) {
        this.drugTakePrescripted = drugTakePrescripted;
    }

}
