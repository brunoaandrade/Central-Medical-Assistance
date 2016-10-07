/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.teste;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macnash
 */
@Entity
@Table(name = "healthCarePro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HealthCarePro.findAll", query = "SELECT h FROM HealthCarePro h"),
    @NamedQuery(name = "HealthCarePro.findById", query = "SELECT h FROM HealthCarePro h WHERE h.id = :id"),
    @NamedQuery(name = "HealthCarePro.findByNCredential", query = "SELECT h FROM HealthCarePro h WHERE h.nCredential = :nCredential"),
    @NamedQuery(name = "HealthCarePro.findByName", query = "SELECT h FROM HealthCarePro h WHERE h.name = :name")})
public class HealthCarePro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "n_credential")
    private int nCredential;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "healthCareProid")
    private Collection<Patient> patientCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "healthCareProid")
    private Collection<Prescription> prescriptionCollection;

    public HealthCarePro() {
    }

    public HealthCarePro(Integer id) {
        this.id = id;
    }

    public HealthCarePro(Integer id, int nCredential, String name) {
        this.id = id;
        this.nCredential = nCredential;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNCredential() {
        return nCredential;
    }

    public void setNCredential(int nCredential) {
        this.nCredential = nCredential;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Patient> getPatientCollection() {
        return patientCollection;
    }

    public void setPatientCollection(Collection<Patient> patientCollection) {
        this.patientCollection = patientCollection;
    }

    @XmlTransient
    public Collection<Prescription> getPrescriptionCollection() {
        return prescriptionCollection;
    }

    public void setPrescriptionCollection(Collection<Prescription> prescriptionCollection) {
        this.prescriptionCollection = prescriptionCollection;
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
        if (!(object instanceof HealthCarePro)) {
            return false;
        }
        HealthCarePro other = (HealthCarePro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.HealthCarePro[ id=" + id + " ]";
    }
    
}
