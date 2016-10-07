/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subClasses;

import entities.HealthCare;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import webServices.GetClass;
import webServices.PostClass;

/**
 *
 * @author Bruno
 */
@SessionScoped
public class Perfil {

    private int idHealthCare = -1;
    private int medicalNumber = -1;
    private String email = "none";
    private String fname = "none";
    private String lname = "none";
    private String addedDate = "none";
    private HealthCare hc = new HealthCare();
    private final GetClass get = new GetClass();
    private final PostClass post = new PostClass();
    private AddPacientes np;
    private String password1 = "";
    private String password2 = "";

    public Perfil(int idDoctor) {
        idHealthCare = idDoctor;
        hc = get.healthCareInformation(idHealthCare);
        np = new AddPacientes(idHealthCare);
        if (hc != null) {
            medicalNumber = hc.getMedicalNumber();
            email = hc.getMail();
            fname = hc.getFname();
            lname = hc.getLname();
            addedDate = hc.getAddedDate().toString();
        }
    }

    public HealthCare getHc() {
        return hc;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setHc(HealthCare hc) {
        this.hc = hc;
    }

    public AddPacientes getNp() {
        return np;
    }

    public void setNp(AddPacientes np) {
        this.np = np;
    }

    public int getIdHealthCare() {
        return idHealthCare;
    }

    public int getMedicalNumber() {
        return medicalNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setEmail(String email) {
        if (email == null ? this.email != null : !email.equals(this.email)) {
            this.email = email;
            if (hc != null) {
                hc.setMail(email);
                boolean success;
                success = post.modifyEmail(hc);
                FacesMessage msg;
                if (success) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Alteração Email");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não conseguio fazer a alteração de email");
                }
                FacesContext.getCurrentInstance().addMessage("email", msg);
            }
        }
    }

    public boolean changePass(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg;
        boolean loggedIn;
        boolean ligBD;

        //webService login
        if (password1 != null && password2 != null && password1.equals(password2) && idHealthCare != -1 && password1.length() > 4) {

            ligBD = post.changePass(idHealthCare, password1);
            if (ligBD) {
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Conseguio Alterar a Password");
            } else {
                loggedIn = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não Conseguio Alterar a Password");
            }

        } else {

            loggedIn = false;
            if (password1.length() < 4) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "A Password tem de ter mais de 4 caracteres");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "As Passwords tem de ser iguais");
            }
        }

        FacesContext.getCurrentInstance().addMessage("pass", msg);
        context.addCallbackParam("loggedIn", loggedIn);
        return loggedIn;
    }

    public void changePassCanc(ActionEvent actionEvent) {

        //webService login
        password1 = "";
        password2 = "";

    }

}
