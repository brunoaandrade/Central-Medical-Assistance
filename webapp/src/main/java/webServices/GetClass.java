/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServices;

import subClasses.Tomas;
import subClasses.Prescricoes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.maps.entities.DrugTakeRegister;
import entities.ActionType;
import entities.Activity;
import entities.BloodPressureMeasure;
import entities.DrugTakePrescripted;
import entities.HealthCare;
import entities.MeasurePrescript;
import entities.MeasureType;
import entities.Patient;
import entities.PrescriptedAction;
import entities.TemperatureMeasure;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class GetClass {

    private UrlWebServices urlWebService = new UrlWebServices();
    private URL url = null;
    private HttpURLConnection con;
    private HttpURLConnection con1;
    private InputStream conect;

    public List<TemperatureMeasure> chartValuesTemp(int id) {
        List<TemperatureMeasure> mylist;
        try {
            url = new URL(urlWebService.getChartValuesTemp() + id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<TemperatureMeasure>>() {
            }.getType();
            mylist = (List<TemperatureMeasure>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public List<BloodPressureMeasure> chartValuesBlood(int id) {
        List<BloodPressureMeasure> mylist;
        try {
            url = new URL(urlWebService.getChartValuesBlood() + id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<BloodPressureMeasure>>() {
            }.getType();
            mylist = (List<BloodPressureMeasure>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public List<Patient> patientList(int idDoctor) {
        List<Patient> mylist;
        try {
            url = new URL(urlWebService.getPatientList() + idDoctor);
            con1 = (HttpURLConnection) url.openConnection();
            con1.setRequestMethod("GET");
            conect = (InputStream) con1.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            java.lang.reflect.Type listType = new TypeToken<List<Patient>>() {
            }.getType();
            mylist = (List<Patient>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public HealthCare healthCareInformation(int id) {
        List<HealthCare> mylist;
        try {
            url = new URL(urlWebService.getHealthCareInformation() + id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<HealthCare>>() {
            }.getType();
            mylist = (List<HealthCare>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        if (mylist == null || mylist.isEmpty()) {
            return null;
        }
        return mylist.get(0);
    }

    public List<Patient> listaPacientesSemMedico() {
        List<Patient> mylist;
        try {
            url = new URL(urlWebService.getListaPacientesSemMedico());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<Patient>>() {
            }.getType();
            mylist = (List<Patient>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public List<Patient> patientFavorit(int idDoctor) {
        List<Patient> p = patientList(idDoctor);
        List<Patient> p1 = new ArrayList<>();

        if (p != null) {
            for (Patient patient : p) {
                if (patient.getFavourite() == 1) {
                    p1.add(patient);
                }
            }
        }
        if (p == null || p1.isEmpty()) {
            p1 = null;
        }
        return p1;
    }

    public List<PrescriptedAction> prescAction(int id) {
        List<PrescriptedAction> mylist;
        try {
            url = new URL(urlWebService.getPrescAction() + id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<PrescriptedAction>>() {
            }.getType();
            mylist = (List<PrescriptedAction>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public List<DrugTakePrescripted> listDrugTake(int id) {
        List<DrugTakePrescripted> mylist;
        try {
            url = new URL(urlWebService.getListDrugTake() + id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<DrugTakePrescripted>>() {
            }.getType();
            mylist = (List<DrugTakePrescripted>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public List<MeasurePrescript> listMeasurePrescript(int id) {
        List<MeasurePrescript> mylist;
        try {
            url = new URL(urlWebService.getListMeasurePrescript() + id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<MeasurePrescript>>() {
            }.getType();
            mylist = (List<MeasurePrescript>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public List<Activity> activityList(int id) {
        List<Activity> mylist;
        try {
            url = new URL(urlWebService.getActivityList() + id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<Activity>>() {
            }.getType();
            mylist = (List<Activity>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public List<DrugTakeRegister> listDrugTakeRegister(int id) {
        List<DrugTakeRegister> mylist;
        try {
            url = new URL(urlWebService.getListDrugTakeRegister() + id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            conect = (InputStream) con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(conect));
            String content = in.readLine();
            Gson gson = new GsonBuilder().create();
            java.lang.reflect.Type listType = new TypeToken<List<DrugTakeRegister>>() {
            }.getType();
            mylist = (List<DrugTakeRegister>) gson.fromJson(content, listType);
        } catch (ProtocolException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GetClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return mylist;
    }

    public List<Prescricoes> listaPresc(int id) {
        List<PrescriptedAction> listPresc = prescAction(id);
        List<Activity> listActivity = activityList(id);
        List<MeasurePrescript> listMeasure = listMeasurePrescript(id);
        List<DrugTakePrescripted> listDrugs = listDrugTake(id);
        List<Prescricoes> list = new ArrayList<>();
        if (listPresc != null) {
            if (listActivity != null) {
                for (Activity actividade : listActivity) {
                    for (PrescriptedAction presc : listPresc) {
                        if (actividade.getIdAction() == presc.getIdPrescriptact()) {

                            String[] parts = actividade.getDescription().split("-");
                            String b = "";
                            if (parts[1] != null) {
                                b = parts[1];
                            }
                            list.add(new Prescricoes(presc.getTimeToStart(), presc.getNumberreps(), presc.getPeriod(), ActionType.Activity, actividade.getIdAction(), actividade.getIdActivity(), parts[0], b));
                            break;
                        }
                    }
                }
            }
            if (listMeasure != null) {
                for (MeasurePrescript measure : listMeasure) {
                    for (PrescriptedAction presc : listPresc) {
                        if (measure.getIdPrescriptact() == presc.getIdPrescriptact()) {
                            String a = "";
                            MeasureType myMethod = measure.getMeasureType();

                            switch (myMethod) {
                                case Blood:
                                    a = "Medida de Pressão Arterial";
                                    break;
                                case Temperature:
                                    a = "Medida de Temperatura";
                                    break;
                                case HeartRate:
                                    a = "Medida de Freq. Cardiaca";
                                    break;
                            }
                            Prescricoes p = new Prescricoes(presc.getTimeToStart(), presc.getNumberreps(), presc.getPeriod(), presc.getActiontype(), presc.getIdAction(), measure.getIdMeasure(), a, "");
                            p.setIdPrescMeasure(presc.getIdPrescriptact());
                            list.add(p);
                            break;
                        }
                    }
                }
            }
            if (listDrugs != null) {
                for (DrugTakePrescripted drug : listDrugs) {
                    for (PrescriptedAction presc : listPresc) {
                        if (drug.getIdAction() == presc.getIdPrescriptact()) {
                            String a = "Medicamento " + drug.getDrugName();
                            Prescricoes p = new Prescricoes(presc.getTimeToStart(), presc.getNumberreps(), presc.getPeriod(), presc.getActiontype(), presc.getIdAction(), drug.getIdTake(), a, "");
                            list.add(p);
                            break;
                        }
                    }
                }
                return list;
            }
        }
        return null;
    }

    public List<Tomas> listaActions(int id) {
        List<TemperatureMeasure> listTemp = chartValuesTemp(id);
        List<BloodPressureMeasure> listBlood = chartValuesBlood(id);
        List<DrugTakeRegister> listDrug = listDrugTakeRegister(id);
        List<Tomas> list = new ArrayList<>();
        Tomas t;
        if (listTemp != null) {
            for (TemperatureMeasure temp : listTemp) {
                t = new Tomas();
                t.setName("Medida Temperatura");
                t.setId(temp.getIdMeasurepresvript());
                t.setDate(temp.getTimeMeasured());
                t.setTempMeasure(temp.gettMeasure());
                t.setAt(ActionType.Measure);
                list.add(t);
            }
        }
        if (listBlood != null) {
            for (BloodPressureMeasure bl : listBlood) {
                t = new Tomas();
                t.setName("Medida Pressão Arterial");
                t.setId(bl.getIdMeasureprescript());
                t.setDate(bl.getTimeMeasured());
                t.setdMeasure(bl.getdMeasure());
                t.setsMeasure(bl.getsMeasure());
                t.setfMeasure(bl.getFreqMeasure());
                t.setAt(ActionType.Measure);
                list.add(t);
            }
        }
        if (listDrug != null) {
            List<DrugTakePrescripted> listDrugs = listDrugTake(id);
            for (DrugTakeRegister temp : listDrug) {
                for (DrugTakePrescripted drug : listDrugs) {
                    if (drug.getIdTake() == temp.getIdTake()) {
                        List<PrescriptedAction> listPresc = prescAction(id);;
                        for (PrescriptedAction presc : listPresc) {
                            if (drug.getIdAction() == presc.getIdAction()) {
                                t = new Tomas();
                                t.setName("Medicamento - " + drug.getDrugName());
                                t.setId(presc.getIdPrescriptact());
                                t.setDate(temp.getTimeTaked());
                                t.setAt(ActionType.Drugtake);
                                list.add(t);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
}
