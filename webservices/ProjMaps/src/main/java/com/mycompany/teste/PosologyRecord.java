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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macnash
 */
@Entity
@Table(name = "posologyRecord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PosologyRecord.findAll", query = "SELECT p FROM PosologyRecord p"),
    @NamedQuery(name = "PosologyRecord.findById", query = "SELECT p FROM PosologyRecord p WHERE p.id = :id"),
    @NamedQuery(name = "PosologyRecord.findByMedicineName", query = "SELECT p FROM PosologyRecord p WHERE p.medicineName = :medicineName"),
    @NamedQuery(name = "PosologyRecord.findByMedicineTakeTime", query = "SELECT p FROM PosologyRecord p WHERE p.medicineTakeTime = :medicineTakeTime"),
    @NamedQuery(name = "PosologyRecord.findByMedicineHowLong", query = "SELECT p FROM PosologyRecord p WHERE p.medicineHowLong = :medicineHowLong")})
public class PosologyRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "medicine_name")
    private String medicineName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "medicine_take_time")
    private String medicineTakeTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "medicine_how_long")
    private String medicineHowLong;
    @JoinColumn(name = "prescription_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prescription prescriptionId;

    public PosologyRecord() {
    }

    public PosologyRecord(Integer id) {
        this.id = id;
    }

    public PosologyRecord(Integer id, String medicineName, String medicineTakeTime, String medicineHowLong) {
        this.id = id;
        this.medicineName = medicineName;
        this.medicineTakeTime = medicineTakeTime;
        this.medicineHowLong = medicineHowLong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineTakeTime() {
        return medicineTakeTime;
    }

    public void setMedicineTakeTime(String medicineTakeTime) {
        this.medicineTakeTime = medicineTakeTime;
    }

    public String getMedicineHowLong() {
        return medicineHowLong;
    }

    public void setMedicineHowLong(String medicineHowLong) {
        this.medicineHowLong = medicineHowLong;
    }

    public Prescription getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Prescription prescriptionId) {
        this.prescriptionId = prescriptionId;
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
        if (!(object instanceof PosologyRecord)) {
            return false;
        }
        PosologyRecord other = (PosologyRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.PosologyRecord[ id=" + id + " ]";
    }
    
}
