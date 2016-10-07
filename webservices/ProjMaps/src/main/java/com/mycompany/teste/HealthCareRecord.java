/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.teste;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macnash
 */
@Entity
@Table(name = "healthCareRecord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HealthCareRecord.findAll", query = "SELECT h FROM HealthCareRecord h"),
    @NamedQuery(name = "HealthCareRecord.findById", query = "SELECT h FROM HealthCareRecord h WHERE h.id = :id"),
    @NamedQuery(name = "HealthCareRecord.findByDate", query = "SELECT h FROM HealthCareRecord h WHERE h.date = :date"),
    @NamedQuery(name = "HealthCareRecord.findByHour", query = "SELECT h FROM HealthCareRecord h WHERE h.hour = :hour"),
    @NamedQuery(name = "HealthCareRecord.findByMeasureType", query = "SELECT h FROM HealthCareRecord h WHERE h.measureType = :measureType")})
public class HealthCareRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "hour")
    @Temporal(TemporalType.TIME)
    private Date hour;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "measure_type")
    private String measureType;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "healthCareRecord")
    private TemperatureRecord temperatureRecord;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "healthCareRecord")
    private BloodPressureRecord bloodPressureRecord;

    public HealthCareRecord() {
    }

    public HealthCareRecord(Integer id) {
        this.id = id;
    }

    public HealthCareRecord(Integer id, Date date, String measureType) {
        this.id = id;
        this.date = date;
        this.measureType = measureType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public TemperatureRecord getTemperatureRecord() {
        return temperatureRecord;
    }

    public void setTemperatureRecord(TemperatureRecord temperatureRecord) {
        this.temperatureRecord = temperatureRecord;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public BloodPressureRecord getBloodPressureRecord() {
        return bloodPressureRecord;
    }

    public void setBloodPressureRecord(BloodPressureRecord bloodPressureRecord) {
        this.bloodPressureRecord = bloodPressureRecord;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HealthCareRecord)) {
            return false;
        }
        HealthCareRecord other = (HealthCareRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.HealthCareRecord[ id=" + id + " ]";
    }
    
}
