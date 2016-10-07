/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subClasses;

import entities.Patient;

/**
 *
 * @author Bruno
 */
public class ListPacientesImage {

    private Patient p;
    private String img;
    private String name;

    public ListPacientesImage(Patient p, String img) {
        this.p = p;
        this.img = img;
        name = p.getFname() + " " + p.getLname();
    }

    public Patient getP() {
        return p;
    }

    public void setP(Patient p) {
        this.p = p;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
