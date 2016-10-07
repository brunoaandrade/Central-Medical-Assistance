/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maps;

import entities.PrescriptedAction;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import webServices.GetClass;
import webServices.PostClass;
import subClasses.Tomas;

/**
 *
 * @author Bruno
 */
@SessionScoped
public class TableAction {

    private List<Tomas> action;
    private Tomas selectedActions;
    private final int idPatient;
    private final GetClass get = new GetClass();
    private final PostClass post = new PostClass();

    public TableAction(int id) {
        idPatient = id;
        action = new ArrayList<>();
        action = get.listaActions(id);
        if (action != null && action.isEmpty()) {
            action = null;
        }
        if (action != null) {
            selectedActions = action.get(0);
        }
    }

    public List<Tomas> getAction() {
        return action;
    }

    public void setAction(List<Tomas> action) {
        this.action = action;
    }

    public Tomas getSelectedActions() {
        return selectedActions;
    }

    public void setSelectedActions(Tomas selectedActions) {
        this.selectedActions = selectedActions;
    }

}
