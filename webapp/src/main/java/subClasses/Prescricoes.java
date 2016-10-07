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
public class Prescricoes {

    Timestamp timeToStart;
    int numberreps;
    int period;
    ActionType actionType;
    int IdAction;
    int idMesureAndActivity;
    String nome;
    String descricao;
    Timestamp timePrescript;
    int idPrescMeasure;

    public Prescricoes(Timestamp timeToStart, int numberreps, int period, ActionType actionType, int IdAction, int idMesureAndActivity, String nome, String descricao) {
        this.timeToStart = timeToStart;
        this.numberreps = numberreps;
        this.period = period;
        this.actionType = actionType;
        this.IdAction = IdAction;
        this.idMesureAndActivity = idMesureAndActivity;
        this.nome = nome;
        this.descricao = descricao;

    }

    public Timestamp getTimeToStart() {
        return timeToStart;
    }

    public int getNumberreps() {
        return numberreps;
    }

    public int getIdPrescMeasure() {
        return idPrescMeasure;
    }

    public void setIdPrescMeasure(int idPrescMeasure) {
        this.idPrescMeasure = idPrescMeasure;
    }

    public int getPeriod() {
        return period;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getIdAction() {
        return IdAction;
    }

    public int getIdMesureAndActivity() {
        return idMesureAndActivity;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Timestamp getTimePrescript() {
        return timePrescript;
    }

}
