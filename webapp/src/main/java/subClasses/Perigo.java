/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subClasses;

import entities.ActionType;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Bruno
 */
public class Perigo implements Serializable {

    int idMeasure = -1;
    int daMeasure;
    int saMeasure;
    int frqMeasure;
    int taMeasure;
    Timestamp time;
    ActionType actionType;
    String nome;
    String paciente;
    int idPaciente;
    boolean visto = false;

    public Perigo(int idPaciente, String paciente, String nome, int idMeasure, int dMeasure, int sMeasure, int frqMeasure, Timestamp time, ActionType actionType) {
        this.idMeasure = idMeasure;
        this.daMeasure = dMeasure;
        this.saMeasure = sMeasure;
        this.frqMeasure = frqMeasure;
        this.time = time;
        this.nome = nome;
        this.actionType = actionType;
        this.idPaciente = idPaciente;
        this.paciente = paciente;

    }

    public Perigo(int idPaciente, String paciente, String nome, int idMeasure, int tMeasure, Timestamp time, ActionType actionType) {
        this.idMeasure = idMeasure;
        this.taMeasure = tMeasure;
        this.time = time;
        this.nome = nome;
        this.actionType = actionType;
        this.idPaciente = idPaciente;
        this.paciente = paciente;

    }

    public int getDaMeasure() {
        return daMeasure;
    }

    public void setDaMeasure(int daMeasure) {
        this.daMeasure = daMeasure;
    }

    public boolean getVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public int getSaMeasure() {
        return saMeasure;
    }

    public void setSaMeasure(int saMeasure) {
        this.saMeasure = saMeasure;
    }

    public int getFrqMeasure() {
        return frqMeasure;
    }

    public void setFrqMeasure(int frqMeasure) {
        this.frqMeasure = frqMeasure;
    }

    public int getTaMeasure() {
        return taMeasure;
    }

    public void setTaMeasure(int taMeasure) {
        this.taMeasure = taMeasure;
    }

    public int getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(int idMeasure) {
        this.idMeasure = idMeasure;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

}
