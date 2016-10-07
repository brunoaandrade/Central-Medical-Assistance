/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServices;

/**
 *
 * @author Bruno
 */
public class UrlWebServices {

    private final String postHealthCareRegister = "http://192.168.160.84:8080/PeiMaps/webresources/healthcareregister";
    private final String postHistory = "http://192.168.160.84:8080/PeiMaps/webresources/changePatientTextHistory";
    private final String postLogin = "http://192.168.160.84:8080/PeiMaps/webresources/healthcarelogin";
    private final String postOneValue = "http://192.168.160.84:8080/PeiMaps/webresources/measurePrescrit";
    private final String postEmail = "http://192.168.160.84:8080/PeiMaps/webresources/changeMailHealthCare";
    private final String addPatientToHealthCare = "http://192.168.160.84:8080/PeiMaps/webresources/changeIdHealthCare";
    private final String postFavorite = "http://192.168.160.84:8080/PeiMaps/webresources/changePatientFavourite?id=";
    private final String postDrugAction = "http://192.168.160.84:8080/PeiMaps/webresources/AddDrugPrescription";
    private final String postActivity = "http://192.168.160.84:8080/PeiMaps/webresources/PrescribeActivity";

    private final String chartValuesTemp = "http://192.168.160.84:8080/PeiMaps/webresources/getTemperatureMeasure?id=";
    private final String chartValuesBlood = "http://192.168.160.84:8080/PeiMaps/webresources/getBPMeasure?id=";
    private final String patientList = "http://192.168.160.84:8080/PeiMaps/webresources/getPatientList?id=";
    private final String healthCareInformation = "http://192.168.160.84:8080/PeiMaps/webresources/HealthCareInfo?id=";
    private final String listaPacientesSemMedico = "http://192.168.160.84:8080/PeiMaps/webresources/GetPatientWithoutHealthCare";
    private final String prescAction = "http://192.168.160.84:8080/PeiMaps/webresources/getListPrescription?id=";
    private final String listDrugTake = "http://192.168.160.84:8080/PeiMaps/webresources/getListDrugTake?id=";
    private final String listMeasurePrescript = "http://192.168.160.84:8080/PeiMaps/webresources/getListMeasurePrescript?id=";
    private final String activityList = "http://192.168.160.84:8080/PeiMaps/webresources/getListActivity?id=";
    private final String listDrugTakeRegister = "http://192.168.160.84:8080/PeiMaps/webresources/getListDrugTakeRegister?id=";

    public String getPostHealthCareRegister() {
        return postHealthCareRegister;
    }

    public String getPostHistory() {
        return postHistory;
    }

    public String getPostLogin() {
        return postLogin;
    }

    public String getPostOneValue() {
        return postOneValue;
    }

    public String getPostEmail() {
        return postEmail;
    }

    public String getAddPatientToHealthCare() {
        return addPatientToHealthCare;
    }

    public String getPostFavorite() {
        return postFavorite;
    }

    public String getPostDrugAction() {
        return postDrugAction;
    }

    public String getPostActivity() {
        return postActivity;
    }

    public String getChartValuesTemp() {
        return chartValuesTemp;
    }

    public String getChartValuesBlood() {
        return chartValuesBlood;
    }

    public String getPatientList() {
        return patientList;
    }

    public String getHealthCareInformation() {
        return healthCareInformation;
    }

    public String getListaPacientesSemMedico() {
        return listaPacientesSemMedico;
    }

    public String getPrescAction() {
        return prescAction;
    }

    public String getListDrugTake() {
        return listDrugTake;
    }

    public String getListMeasurePrescript() {
        return listMeasurePrescript;
    }

    public String getActivityList() {
        return activityList;
    }

    public String getListDrugTakeRegister() {
        return listDrugTakeRegister;
    }

}
