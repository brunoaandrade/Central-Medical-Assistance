/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.sqlcon;

/** 
 *
 * @author MoLt1eS
 */
public final class SPCall {
    
    /*PATIENT*/
    
    //Patient (sns, mail, passwordHash, fname, lname, out result)
    public final static String addPatient = "{call addPatient(?, ?, ?, ?, ?, ?)}";
    
    // REVER - IN MAIL, IN PW, OUT IDPATIENT
    public final static String getPatientInfo = "{call getPatientInfo(?, ?)}";
    //getPatientInfo ??

    // IN mail, IN passwordHash, OUT patientID
    public final static String getPatientID = "{call getPatientID(?, ?, ?)}";
    
    // IN idPatient,IN token, OUT status
    public final static String addPatientToken = "{call addPatientToken(?, ?, ?)}";
    
    // REVER - IN idPatient, OUT Boolean
    public final static String hasPatientToken = "{call hasPatientToken(?, ?)}";
    
    //IN idPatient, OUT token, OUT status
    public final static String getPatientToken = "{call getPatientToken(?, ?, ?)}";
    
    //REVER - TEMPORARY FIX IN idPatient, OUT token
    public final static String gettPatientToken = "{call gettPatientToken(?, ?)}";
    
    //POR FAZER - IN token varchar(128), OUT @value boolean 
    public static String existsPatientToken = "{call existsPatientToken(?, ?)}";
    
    //REVER getPatientInfo - IN token, OUT SNS, OUT MAIL, OUT FNAME, OUT LNAME
    public static String getPatientByToken = "{call getPatientByToken(?, ?, ?, ?, ?)}";
    
    
    
    
    /*HEALTH CARE PRO*/
    
    //in healthcareid int, in tokentoinsert varchar(128), out result int
    public final static String addHealthCareToken ="{call addHealthCareToken(?,?,?)}";
    
    //IN MedicalNumber int, IN Mail, IN passwordHash, IN fname, IN Lname, out res
    public final static String addHealthCarePro = "{call registoMedico(?, ?, ?, ?, ?, ?)}";

    //POR FAZER - 
    public static String getHealthCareByToken;
    
    //in mail varchar(30), in passwordhash varchar(40), out healthcareid int, out result int
    public final static String getHealthCareID = "{call getHealthCareID (?,?,?,?)}";
    
    
    //in idHealthCare, out result boolean
    public final static String hasHealthCareToken = "{call hasHealhtCareToken (?,?)}";
    //IN idPatient, IN newText, OUT result
    public final static String changeTextHistory = "{call changeTextHistory (?,?,?)}";
    
    public final static String patientFavourite = "{call patientFavourite (?,?)}";

    //IN idPatient, OUT Token
    public final static String getHealthCareToken = "{call getHealthCareToken (?,?,?)}";
    
    //IN idPatient, IN newText, OUT result
    public final static String editDocMail = "{call editDocMail (?,?,?)}";
    
     //IN idPatient, IN newText, OUT result
    public final static String assocDoctoPatient = "{call assocDoctoPatient (?,?,?)}";
    
    //ACTIONS
    
    //get prescripted actions per pacient, used by medic
    
    //A implementar - IN idPatient, IN idHealthCarePro, IN timeToStart, IN period, IN numberreps, IN measureType, OUT result
    public static String insertPrescriptedMeasure = "{call insertPrescriptedMeasure (?,?,?,?,?,?,?,?)}";
    
    /*BLOOD PRESSURE*/
    //in patientid int, out result int
    public static String getFreeBPMeasure = "{call getFreeBPMeasure (?,?)}";
    //in patientid int, out result int
    public static String getPrescriptBPMeasure = "{call getPrescriptBPMeasure (?,?)}";
    
    // in idAction in Actiontype out result
    public static String listActiondetails = "{call listActiondetails (?,?,?)}";
    
    /*in idPatient int(11), 
    in dMeasure int(11),in sMeasure int(11),in freqMeasure int(11), 
    in timeMeasured timestamp, out result int*/
    public static String insertFreeBloodPressMeasure = "{call insertFreeBloodPressMeasure (?,?,?,?,?,?)}";
    /*in idMeasure int(11), in dMeasure int(11),
    in sMeasure int(11),in freqMeasure int(11),in timeMeasured timestamp, out result int*/
    public static String insertPrescrBloodMeasure = "{call insertPrescrBloodMeasure (?,?,?,?,?,?)}";
    
    
            
    /*TEMPERATURE*/
    //in patientid int, out result int - devolver
    public static String FreeTempMeasure= "{call FreeTempMeauser (?,?)}";
    //in patientid int, out result int - devolver
    public static String PrescriptTempMeasure = "{call PrescriptTempMeauser (?,?)}";
    /*in idPatient int(11), 
    in tMeasure int(11), in timeMeasured timestamp, out result int*/
    public static String insertFreeTempMeasure = "{call insertFreeTempMeasure (?,?,?,?)}";
    /*in idMeasure int(11), in tMeasure int(11),
    in timeMeasured timestamp, out result int*/
    public static String insertPrescrTempMeasure = "{call insertPrescrTempMeasure (?,?,?,?)}";
    
    
    /*DRUG TAKE*/
    /*in idPatient int(11), 
    in idHealthCare int(11), in timeToStart TIMESTAMP, in period int(10),
    in numberreps int(10), in drugName varchar(20), in description varchar(500),
    out result int, out idTakedrug int (11)*/
    public static String insertPrescriptedDrug = "{call insertPrescriptedDrug (?,?,?,?,?,?,?,?,?)}";
    
    /*ACTIVITY*/
    /*in idPatient int(11), 
    in idHealthCare int(11), in timeToStart TIMESTAMP, in description varchar (5000),
    out result int, out idActivity int(11)*/
    public static String insertPrescriptedActivity = "{call insertPrescriptedActivity (?,?,?,?,?,?)}";
    
    public static String patientPrescriptions = "{call patientPrescriptions (?,?)}";
    
    public static String drugTakeList = "{call drugTakeList (?,?)}";
    
    public static String activityList = "{call activityList (?,?)}";
    
    public static String measureprescriptList = "{call measureprescriptList (?,?)}";
    
    public static String drugTakeregisterList = "{call drugTakeregisterList (?,?)}";


    //Mobile
    //Register:
    //deviceMobRegistry (in marca varchar(15), in modelo varchar(15),in firmware varchar (20), out result int)
    
    
    //deviceMobRegistry (in marca varchar(15), in modelo varchar(15),in firmware varchar (20),tokendevices varchar(40), out result int)
    public static String deviceMobRegistry = "{call deviceMobRegistry (?,?,?,?,?,?)}";
    
    //hastokendevice (in tokendev varchar(20), in idpatient int(11),out result int)  //0 existe -1 n existe
    public static String hastokendevice = "{call hastokendevice(?,?,?)}";
    
    // listDevicespatient (in idpatient int(11), out result int)
    public static String listDevicespatient = "{call listDevicespatient (?,?)}";
    
    
    // insertDrugRegister (in idTake int (11), in timeTaked timestamp,out result int)
    public static String insertDrugRegister = "{call insertDrugRegister(?,?,?)}";
}
