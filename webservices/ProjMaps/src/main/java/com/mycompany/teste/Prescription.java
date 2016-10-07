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
import javax.persistence.OneToOne;
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
@Table(name = "prescription")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prescription.findAll", query = "SELECT p FROM Prescription p"),
    @NamedQuery(name = "Prescription.findById", query = "SELECT p FROM Prescription p WHERE p.id = :id"),
    @NamedQuery(name = "Prescription.findByDate", query = "SELECT p FROM Prescription p WHERE p.date = :date"),
    @NamedQuery(name = "Prescription.findByDateExpiration", query = "SELECT p FROM Prescription p WHERE p.dateExpiration = :dateExpiration"),
    @NamedQuery(name = "Prescription.findByRoutineExtras", query = "SELECT p FROM Prescription p WHERE p.routineExtras = :routineExtras")})
public class Prescription implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_expiration")
    @Temporal(TemporalType.DATE)
    private Date dateExpiration;
    @Size(max = 1000)
    @Column(name = "routine_extras")
    private String routineExtras;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prescriptionId")
    private Collection<PosologyRecord> posologyRecordCollection;
    @JoinColumn(name = "healthCarePro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private HealthCarePro healthCareProid;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "prescription")
    private PrescriptionRecord prescriptionRecord;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prescriptionId")
    private Collection<Measures> measuresCollection;

    public Prescription() {
    }

    public Prescription(Integer id) {
        this.id = id;
    }

    public Prescription(Integer id, Date date, Date dateExpiration) {
        this.id = id;
        this.date = date;
        this.dateExpiration = dateExpiration;
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

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getRoutineExtras() {
        return routineExtras;
    }

    public void setRoutineExtras(String routineExtras) {
        this.routineExtras = routineExtras;
    }

    @XmlTransient
    public Collection<PosologyRecord> getPosologyRecordCollection() {
        return posologyRecordCollection;
    }

    public void setPosologyRecordCollection(Collection<PosologyRecord> posologyRecordCollection) {
        this.posologyRecordCollection = posologyRecordCollection;
    }

    public HealthCarePro getHealthCareProid() {
        return healthCareProid;
    }

    public void setHealthCareProid(HealthCarePro healthCareProid) {
        this.healthCareProid = healthCareProid;
    }

    public PrescriptionRecord getPrescriptionRecord() {
        return prescriptionRecord;
    }

    public void setPrescriptionRecord(PrescriptionRecord prescriptionRecord) {
        this.prescriptionRecord = prescriptionRecord;
    }

    @XmlTransient
    public Collection<Measures> getMeasuresCollection() {
        return measuresCollection;
    }

    public void setMeasuresCollection(Collection<Measures> measuresCollection) {
        this.measuresCollection = measuresCollection;
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
        if (!(object instanceof Prescription)) {
            return false;
        }
        Prescription other = (Prescription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.Prescription[ id=" + id + " ]";
    }
    
}
