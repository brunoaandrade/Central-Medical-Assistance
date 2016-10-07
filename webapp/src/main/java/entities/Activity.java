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
public class Activity implements Serializable {

    private int idActivity;
    private int idAction;
    private String description;

    public Activity() {
    }

    public Activity(int idAction, String description) {
        this.idAction = idAction;
        this.description = description;
    }

    public Activity(int idActivity, int idAction, String description) {
        this.idActivity = idActivity;
        this.idAction = idAction;
        this.description = description;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Activity{" + "idActivity=" + idActivity + ", idAction=" + idAction + ", description=" + description + '}';
    }

}
