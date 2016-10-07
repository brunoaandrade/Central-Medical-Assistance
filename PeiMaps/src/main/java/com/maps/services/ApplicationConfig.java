/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author MoLt1eS
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.maps.entities.RegisterBPMResource.class);
        resources.add(com.maps.services.AddDrugPrescription.class);
        resources.add(com.maps.services.AndroidGetActionListResource.class);
        resources.add(com.maps.services.AndroidGetPatientInfoResource.class);
        resources.add(com.maps.services.ChangeIdHealthCareResource.class);
        resources.add(com.maps.services.ChangeMailHealthCareResource.class);
        resources.add(com.maps.services.ChangePatientFavouriteResource.class);
        resources.add(com.maps.services.ChangePatientTextHistory.class);
        resources.add(com.maps.services.GetBPMeasureResource.class);
        resources.add(com.maps.services.GetListActivityResource.class);
        resources.add(com.maps.services.GetListDrugTakeRegisterResource.class);
        resources.add(com.maps.services.GetListDrugTakeResource.class);
        resources.add(com.maps.services.GetListMeasurePrescriptResource.class);
        resources.add(com.maps.services.GetListPrescriptionResource.class);
        resources.add(com.maps.services.GetMeasureResource.class);
        resources.add(com.maps.services.GetPatientActivityResource.class);
        resources.add(com.maps.services.GetPatientDrugPrescribedResource.class);
        resources.add(com.maps.services.GetPatientListResource.class);
        resources.add(com.maps.services.GetPatientMeasurePrescriptResource.class);
        resources.add(com.maps.services.GetPatientTokenResource.class);
        resources.add(com.maps.services.GetPatientWithoutHealthCareResource.class);
        resources.add(com.maps.services.GetTemperatureMeasureResource.class);
        resources.add(com.maps.services.HealthCareInfoResource.class);
        resources.add(com.maps.services.HealthCareLoginResource.class);
        resources.add(com.maps.services.HealthCareRegister.class);
        resources.add(com.maps.services.MeasurePrescritResource.class);
        resources.add(com.maps.services.PatientLogin.class);
        resources.add(com.maps.services.PatientRegister.class);
        resources.add(com.maps.services.PatientResource.class);
        resources.add(com.maps.services.PrescribeActivityRes.class);
        resources.add(com.maps.services.RegisterDrugTakeResource.class);
        resources.add(com.maps.services.RegisterTempResource.class);
        resources.add(com.maps.services.RegistoPrescricaoResource.class);
    }
    
}
