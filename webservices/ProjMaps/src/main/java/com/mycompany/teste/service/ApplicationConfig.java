/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.teste.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author macnash
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
        resources.add(com.mycompany.mytest.HeathCareResource.class);
        resources.add(com.mycompany.teste.service.BloodPressureRecordFacadeREST.class);
        resources.add(com.mycompany.teste.service.HealthCareProFacadeREST.class);
        resources.add(com.mycompany.teste.service.HealthCareRecordFacadeREST.class);
        resources.add(com.mycompany.teste.service.MeasuresFacadeREST.class);
        resources.add(com.mycompany.teste.service.PatientFacadeREST.class);
        resources.add(com.mycompany.teste.service.PosologyRecordFacadeREST.class);
        resources.add(com.mycompany.teste.service.PrescriptionFacadeREST.class);
        resources.add(com.mycompany.teste.service.PrescriptionRecordFacadeREST.class);
        resources.add(com.mycompany.teste.service.TemperatureRecordFacadeREST.class);
    }
    
}
