/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maps;

import webServices.PostClass;
import entities.HealthCareToken;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Bruno
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    private int idUser;
    private String tokenHC;
    private String username;
    private String password;
    private final PostClass post = new PostClass();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenHC() {
        return tokenHC;
    }

    public int getIdUser() {
        return idUser;
    }

    public boolean login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg;
        boolean loggedIn;
        HealthCareToken hct = post.postLogin(username, password);

        //webService login
        if ((username != null && username.equals("admin") && password != null && password.equals("a")) || hct != null) {
            if (username.equals("admin")) {
                idUser = 64;
            } else {
                idUser = hct.getIdHealthCare();
                tokenHC = hct.getToken();
            }
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);

        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        if (loggedIn) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return loggedIn;
    }

}
