/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 *
 * @author macnash
 */
public class PrescribedActivity {

    private PatientAction patientAction;
    private PrescriptedAction prescriptedAction;
    private Activity activity;

    public PrescribedActivity() {
    }

    @Override
    public String toString() {
        return "PrescribedActivity{" + "patientAction=" + patientAction + ", prescriptedAction=" + prescriptedAction + ", activity=" + activity + '}';
    }

    public PrescribedActivity(PatientAction patientAction, PrescriptedAction prescriptedAction, Activity activity) {
        this.patientAction = patientAction;
        this.prescriptedAction = prescriptedAction;
        this.activity = activity;
    }

    public PatientAction getPatientAction() {
        return patientAction;
    }

    public PrescriptedAction getPrescriptedAction() {
        return prescriptedAction;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setPatientAction(PatientAction patientAction) {
        this.patientAction = patientAction;
    }

    public void setPrescriptedAction(PrescriptedAction prescriptedAction) {
        this.prescriptedAction = prescriptedAction;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
