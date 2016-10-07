/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.teste;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macnash
 */
@Entity
@Table(name = "prescriptionRecord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrescriptionRecord.findAll", query = "SELECT p FROM PrescriptionRecord p"),
    @NamedQuery(name = "PrescriptionRecord.findByPrescriptionId", query = "SELECT p FROM PrescriptionRecord p WHERE p.prescriptionId = :prescriptionId"),
    @NamedQuery(name = "PrescriptionRecord.findByState", query = "SELECT p FROM PrescriptionRecord p WHERE p.state = :state"),
    @NamedQuery(name = "PrescriptionRecord.findByDelivered", query = "SELECT p FROM PrescriptionRecord p WHERE p.delivered = :delivered")})
public class PrescriptionRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "prescription_id")
    private Integer prescriptionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "State")
    private boolean state;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Delivered")
    private boolean delivered;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;
    @JoinColumn(name = "prescription_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Prescription prescription;

    public PrescriptionRecord() {
    }

    public PrescriptionRecord(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public PrescriptionRecord(Integer prescriptionId, boolean state, boolean delivered) {
        this.prescriptionId = prescriptionId;
        this.state = state;
        this.delivered = delivered;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prescriptionId != null ? prescriptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrescriptionRecord)) {
            return false;
        }
        PrescriptionRecord other = (PrescriptionRecord) object;
        if ((this.prescriptionId == null && other.prescriptionId != null) || (this.prescriptionId != null && !this.prescriptionId.equals(other.prescriptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.PrescriptionRecord[ prescriptionId=" + prescriptionId + " ]";
    }
    
}
