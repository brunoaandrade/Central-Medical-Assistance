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
public class FreeAction implements Serializable {

    private int idFreeact;
    private int idAction;
    private ActionType actiontype;

    public FreeAction() {
    }

    public FreeAction(int idFreeact, int idAction, ActionType actiontype) {
        this.idFreeact = idFreeact;
        this.idAction = idAction;
        this.actiontype = actiontype;
    }

    public FreeAction(int idAction, ActionType actiontype) {
        this.idAction = idAction;
        this.actiontype = actiontype;
    }

    public int getIdFreeact() {
        return idFreeact;
    }

    public void setIdFreeact(int idFreeact) {
        this.idFreeact = idFreeact;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public ActionType getActiontype() {
        return actiontype;
    }

    public void setActiontype(ActionType actiontype) {
        this.actiontype = actiontype;
    }

    @Override
    public String toString() {
        return "FreeAction{" + "idFreeact=" + idFreeact + ", idAction=" + idAction + ", actiontype=" + actiontype + '}';
    }

}
