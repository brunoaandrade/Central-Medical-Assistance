/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.teste;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macnash
 */
@Entity
@Table(name = "patient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
    @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id"),
    @NamedQuery(name = "Patient.findByNSns", query = "SELECT p FROM Patient p WHERE p.nSns = :nSns"),
    @NamedQuery(name = "Patient.findByName", query = "SELECT p FROM Patient p WHERE p.name = :name"),
    @NamedQuery(name = "Patient.findByGender", query = "SELECT p FROM Patient p WHERE p.gender = :gender"),
    @NamedQuery(name = "Patient.findByBirthdate", query = "SELECT p FROM Patient p WHERE p.birthdate = :birthdate"),
    @NamedQuery(name = "Patient.findByAddress", query = "SELECT p FROM Patient p WHERE p.address = :address"),
    @NamedQuery(name = "Patient.findByHealthConditions", query = "SELECT p FROM Patient p WHERE p.healthConditions = :healthConditions")})
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "n_sns")
    private int nSns;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Size(max = 45)
    @Column(name = "address")
    private String address;
    @Size(max = 1000)
    @Column(name = "health_conditions")
    private String healthConditions;
    @JoinColumn(name = "healthCarePro_id", referencedColumnName = "id")
    @ManyToOne
    private HealthCarePro healthCareProid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<HealthCareRecord> healthCareRecordCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<PrescriptionRecord> prescriptionRecordCollection;

    public Patient() {
    }

    public Patient(Integer id) {
        this.id = id;
    }

    public Patient(Integer id, int nSns, String name, Date birthdate) {
        this.id = id;
        this.nSns = nSns;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNSns() {
        return nSns;
    }

    public void setNSns(int nSns) {
        this.nSns = nSns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHealthConditions() {
        return healthConditions;
    }

    public void setHealthConditions(String healthConditions) {
        this.healthConditions = healthConditions;
    }

    public HealthCarePro getHealthCareProid() {
        return healthCareProid;
    }

    public void setHealthCareProid(HealthCarePro healthCareProid) {
        this.healthCareProid = healthCareProid;
    }

    @XmlTransient
    public Collection<HealthCareRecord> getHealthCareRecordCollection() {
        return healthCareRecordCollection;
    }

    public void setHealthCareRecordCollection(Collection<HealthCareRecord> healthCareRecordCollection) {
        this.healthCareRecordCollection = healthCareRecordCollection;
    }

    @XmlTransient
    public Collection<PrescriptionRecord> getPrescriptionRecordCollection() {
        return prescriptionRecordCollection;
    }

    public void setPrescriptionRecordCollection(Collection<PrescriptionRecord> prescriptionRecordCollection) {
        this.prescriptionRecordCollection = prescriptionRecordCollection;
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
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.Patient[ id=" + id + " ]";
    }
    
}
