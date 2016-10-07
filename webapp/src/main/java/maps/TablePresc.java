/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maps;

import entities.PrescriptedAction;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import webServices.GetClass;
import webServices.PostClass;
import subClasses.Prescricoes;

/**
 *
 * @author Bruno
 */
@ManagedBean
@SessionScoped
public class TablePresc {

    private List<Prescricoes> prescricoes;
    private Prescricoes selectedPrescricoes;
    private final int idPatient;
    private final GetClass get = new GetClass();
    private final PostClass post = new PostClass();
    private boolean renderList = true;
    private boolean renderCal = false;

    public TablePresc(int id) {
        idPatient = id;
        prescricoes = new ArrayList<>();
        prescricoes = get.listaPresc(id);
        if (prescricoes != null && prescricoes.isEmpty()) {
            prescricoes = null;
        }
        if (prescricoes != null) {
            selectedPrescricoes = prescricoes.get(0);
        }
    }

    public List<Prescricoes> getPrescricoes() {
        update();
        return prescricoes;
    }

    public Prescricoes getSelectedPrescricoes() {
        return selectedPrescricoes;
    }

    public boolean getRenderList() {
        return renderList;
    }

    public boolean getRenderCal() {
        return renderCal;
    }

    public void setSelectedPrescricoes(Prescricoes selectedPrescricoes) {
        this.selectedPrescricoes = selectedPrescricoes;
    }

    public void seeDetail() {
        String type = selectedPrescricoes.getActionType().toString();
        switch (type) {
            case ("Activity"):
                //ac = get.activity(selectedPrescricoes.getIdAction(), selectedPrescricoes.getActiontype().toString());
                System.out.print("Activity");
                break;
            case ("Drugtake"):
                //dr = get.activity(selectedPrescricoes.getIdAction(), selectedPrescricoes.getActiontype().toString());
                System.out.print("Drugtake");
                break;
            case ("Measure"):
                //me = get.activity(selectedPrescricoes.getIdAction(), selectedPrescricoes.getActiontype().toString());
                System.out.print("Measure");
                break;
        }
    }

    public void delete() {
        boolean success = post.deletePrescription(selectedPrescricoes.getIdAction());
        FacesMessage message;
        if (success) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Prescrição Apagada", "Prescrição id: " + selectedPrescricoes.getIdAction());
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Insucesso na operação", "Prescrição id: " + selectedPrescricoes.getIdAction() + " não apagada, erro na ligação");
        }
        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage("editarPrescr", message);
    }

    void update() {
        List<Prescricoes> p;
        p = get.listaPresc(idPatient);
        if (p != null) {
            prescricoes = p;
        }
    }

    public void renderL() {
        renderList = true;
        renderCal = false;
    }

    public void renderC() {
        renderCal = true;
        renderList = false;
    }
}
