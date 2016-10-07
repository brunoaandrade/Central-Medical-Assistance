/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maps;

import entities.Patient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import webServices.GetClass;
import subClasses.ListPacientesImage;

/**
 *
 * @author Bruno
 */
@ViewScoped
@SessionScoped
public class DataGrid {

    private List<ListPacientesImage> listComplete;
    private List<ListPacientesImage> listSearch;
    private List<ListPacientesImage> list1;
    private List<ListPacientesImage> mylist;
    private String searchBox = "";
    private ListPacientesImage selectedCar;
    GetClass getClass = new GetClass();
    private int numberLow = 100;
    private int numberHigh = 1;
    private int numberLowAllow = 100;
    private int numberHighAllow = 1;
    private String gender = "N";
    private boolean addRecentle = false;

    public DataGrid(int idDoctor) {
        List<Patient> mylistPatient = getClass.patientList(idDoctor);
        if (mylistPatient != null) {
            listComplete = new ArrayList<>();
            for (Patient mylistPatient1 : mylistPatient) {
                ListPacientesImage pi = new ListPacientesImage(mylistPatient1, findImage(mylistPatient1.getGender(), mylistPatient1.getSns()));
                listComplete.add(pi);
            }
            selectedCar = listComplete.get(0);

        }

        mylist = listComplete;
        listSearch = mylist;
        actualizarAge();
    }

    public List<ListPacientesImage> getCars() {
        return mylist;
    }

    public boolean isAddRecentle() {
        return addRecentle;
    }

    public void setAddRecentle(boolean addRecentle) {
        this.addRecentle = addRecentle;
    }

    public int getNumberLow() {
        return numberLow;
    }

    public void setNumberLow(int numberLow) {
        this.numberLow = numberLow;
    }

    public int getNumberHigh() {
        return numberHigh;
    }

    public void setNumberHigh(int numberHigh) {
        this.numberHigh = numberHigh;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String Gender) {
        this.gender = Gender;
    }

    public ListPacientesImage getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(ListPacientesImage selectedCar) {
        this.selectedCar = selectedCar;
    }

    public String getSearchBox() {
        return searchBox;
    }

    public void setSearchBox(String searchBox) {
        this.searchBox = searchBox;
    }

    public int getNumberLowAllow() {
        return numberLowAllow;
    }

    public void setNumberLowAllow(int numberLowAllow) {
        this.numberLowAllow = numberLowAllow;
    }

    public int getNumberHighAllow() {
        return numberHighAllow;
    }

    public void setNumberHighAllow(int numberHighAllow) {
        this.numberHighAllow = numberHighAllow;
    }

    private String findImage(String g, int number) {
        String imagem;
        if ("M".equals(g)) {
            while (number > 20) {
                number -= 21;
            }
            imagem = "img/Pacientes/men/fotoMen (" + number + ").jpg";
        } else {
            while (number > 20) {
                number -= 21;
            }
            imagem = "img/Pacientes/women/fotoWomen (" + number + ").jpg";
        }
        return imagem;
    }

    public void go() {
        try {
            searchBox = "";
            mylist = listComplete;
            listSearch = listComplete;
            gender = "N";
            actualizarAge();
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(DataGrid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goIndice() {
        try {
            searchBox = "";
            mylist = listComplete;
            listSearch = listComplete;
            gender = "N";
            actualizarAge();
            FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(DataGrid.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void search() {
        addRecentle = false;
        gender = "N";
        if (!"".equals(searchBox)) {
            mylist = new ArrayList<>();
            for (ListPacientesImage listComplete1 : listComplete) {
                if (listComplete1.getName().toLowerCase().contains(searchBox.toLowerCase())) {
                    mylist.add(listComplete1);
                }
            }
            for (ListPacientesImage listComplete1 : listComplete) {
                String s = "" + listComplete1.getP().getSns();
                if (s.contains(searchBox)) {
                    if (!mylist.equals(listComplete1)) {
                        mylist.add(listComplete1);
                    }
                }
            }
        } else {
            mylist = listComplete;
        }
        listSearch = mylist;
        actualizarAge();
    }

    public void genderChange() {
        mylist = new ArrayList<>();
        switch (gender) {
            case "M":
                for (ListPacientesImage listComplete1 : listSearch) {
                    if (listComplete1.getP().getGender().equals("M")) {
                        mylist.add(listComplete1);
                    }
                }
                break;
            case "N":
                mylist = listSearch;
                break;
            default:
                for (ListPacientesImage listComplete1 : listSearch) {
                    if (listComplete1.getP().getGender().equals("F")) {
                        mylist.add(listComplete1);
                    }
                }
                break;
        }
        if (addRecentle) {
            list1 = mylist;
            mylist = new ArrayList<>();
            for (ListPacientesImage listComplete1 : list1) {
                if (Days.daysBetween(new DateTime(listComplete1.getP().getAddedDate()), new DateTime()).getDays() < 15) {
                    mylist.add(listComplete1);
                }
            }
        }
        if (numberLowAllow != numberLow || numberHighAllow != numberHigh) {
            list1 = mylist;
            mylist = new ArrayList<>();
            for (ListPacientesImage listComplete1 : list1) {
                if (listComplete1.getP().getBorndate() != null) {
                    if (getAge(listComplete1.getP().getBorndate()) <= numberHigh && (getAge(listComplete1.getP().getBorndate()) >= numberLow)) {
                        mylist.add(listComplete1);
                    }
                } else {
                    mylist.add(listComplete1);
                }
            }
        }

    }

    private void actualizarAge() {
        if (mylist != null) {
            numberLow = 100;
            numberHigh = 1;
            for (ListPacientesImage listComplete1 : mylist) {
                if (listComplete1.getP().getBorndate() != null) {
                    if (getAge(listComplete1.getP().getBorndate()) > numberHigh) {
                        numberHigh = getAge(listComplete1.getP().getBorndate());
                    }
                    if (getAge(listComplete1.getP().getBorndate()) < numberLow) {
                        numberLow = getAge(listComplete1.getP().getBorndate());
                    }
                }
            }
            numberLowAllow = numberLow;
            numberHighAllow = numberHigh;
        }
    }

    private int getAge(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        LocalDate birthdate = new LocalDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        LocalDate now = new LocalDate();
        return Years.yearsBetween(birthdate, now).getYears();
    }
}
