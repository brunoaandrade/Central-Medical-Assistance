/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subClasses;

import entities.ActionType;
import entities.BloodPressureMeasure;
import entities.Patient;
import entities.TemperatureMeasure;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.primefaces.context.RequestContext;
import webServices.GetClass;

/**
 *
 * @author Bruno
 */
@ManagedBean
@ViewScoped
public class ListaPerigo implements Serializable {

    private List<Perigo> visto = new ArrayList<>();
    private String title = "";
    int nOcurencias = 0;
    private int doctor = -1;
    private List<Perigo> lista = null;
    private final GetClass getClass = new GetClass();
    private List<Patient> pacientes = null;
    private boolean haPerigo = false;

    public ListaPerigo(int idDoctor) {
        doctor = idDoctor;
        lista = new ArrayList<>();
        pacientes = new ArrayList<>();
        pacientes = getClass.patientList(doctor);
        if (pacientes != null) {
            nOcurencias = 0;
            for (Patient p : pacientes) {
                valoresTemp(p);
                valoresBP(p);
            }
            haPerigo = 0 != lista.size();
        }

    }

    public void update() {
        pacientes = getClass.patientList(doctor);
        lista = new ArrayList<>();
        if (pacientes != null) {
            nOcurencias = 0;
            for (Patient p : pacientes) {
                valoresTemp(p);
                valoresBP(p);
            }
            haPerigo = 0 != lista.size();
        }
    }

    private void valoresTemp(Patient p) {
        int id = p.getIdPatient();
        List<TemperatureMeasure> mylist;
        if (id != -1) {
            mylist = getClass.chartValuesTemp(id);
        } else {
            mylist = null;
        }
        if (mylist != null) {
            for (TemperatureMeasure mylist1 : mylist) {
                if ((mylist1.gettMeasure() < 36 || mylist1.gettMeasure() > 38)) {
                    if (Hours.hoursBetween(new DateTime(mylist1.getTimeMeasured()), new DateTime()).getHours() < 10) {
                        Perigo pa = new Perigo(id, p.getFname() + " " + p.getLname(), "Temp", mylist1.getIdTMeasure(), mylist1.gettMeasure(), mylist1.getTimeMeasured(), ActionType.Measure);
                        boolean jaVisto = false;
                        if (!visto.isEmpty()) {
                            for (Perigo perigo : visto) {
                                if (perigo.idMeasure == pa.getIdMeasure()) {
                                    jaVisto = true;
                                }
                            }
                            if (!jaVisto) {
                                lista.add(pa);
                                nOcurencias++;
                            }
                        } else {
                            lista.add(pa);
                            nOcurencias++;
                        }
                    }
                }
            }
        }
    }

    private void valoresBP(Patient p) {
        int id = p.getIdPatient();
        List<BloodPressureMeasure> mylist;
        if (id != -1) {
            mylist = getClass.chartValuesBlood(id);
        } else {
            mylist = null;
        }
        if (mylist != null) {
            for (BloodPressureMeasure mylist1 : mylist) {
                if ((mylist1.getdMeasure() < 80 || mylist1.getdMeasure() > 90 || mylist1.getsMeasure() < 120 || mylist1.getsMeasure() > 140)) {
                    if (Hours.hoursBetween(new DateTime(mylist1.getTimeMeasured()), new DateTime()).getHours() < 10) {
                        Perigo pa = new Perigo(id, p.getFname() + " " + p.getLname(), "PArterial", mylist1.getIdBPMeasure(), mylist1.getdMeasure(), mylist1.getsMeasure(), mylist1.getFreqMeasure(), mylist1.getTimeMeasured(), ActionType.Measure);
                        boolean jaVisto = false;
                        if (!visto.isEmpty()) {
                            for (Perigo perigo : visto) {
                                if (perigo.idMeasure == pa.getIdMeasure()) {
                                    jaVisto = true;
                                }
                            }
                            if (!jaVisto) {
                                lista.add(pa);
                                nOcurencias++;
                            }
                        } else {
                            lista.add(pa);
                            nOcurencias++;
                        }
                    }
                }
            }
        }
    }

    public List<Perigo> getLista() {
        return lista;
    }

    public void setLista(List<Perigo> lista) {
        this.lista = lista;
    }

    public void open() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        //options.put("draggable", false);
        options.put("resizable", false);
        // options.put("contentHeight", 320);
        options.put("height", "550");
        options.put("width", "700");
        RequestContext.getCurrentInstance().openDialog("dialog/perigo", options, null);
    }

    public boolean getHaPerigo() {
        return haPerigo;
    }

    public void setHaPerigo(boolean haPerigo) {
        this.haPerigo = haPerigo;
    }

    public void selectCarFromDialog(Perigo perigo) {
        RequestContext.getCurrentInstance().closeDialog(perigo);
    }

    public void selectCarToDelete(Perigo perigo) {
        visto.add(perigo);
        update();
    }

    public String getTitle() {
        title = nOcurencias + " Avisos";
        return title;
    }

    public boolean isHaPerigo() {
        return haPerigo;
    }
}
