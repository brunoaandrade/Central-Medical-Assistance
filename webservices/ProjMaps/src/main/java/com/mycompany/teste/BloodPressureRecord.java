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
@Table(name = "bloodPressureRecord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BloodPressureRecord.findAll", query = "SELECT b FROM BloodPressureRecord b"),
    @NamedQuery(name = "BloodPressureRecord.findByHealthCareRecordid", query = "SELECT b FROM BloodPressureRecord b WHERE b.healthCareRecordid = :healthCareRecordid"),
    @NamedQuery(name = "BloodPressureRecord.findByValueSystolic", query = "SELECT b FROM BloodPressureRecord b WHERE b.valueSystolic = :valueSystolic"),
    @NamedQuery(name = "BloodPressureRecord.findByValueDiastolic", query = "SELECT b FROM BloodPressureRecord b WHERE b.valueDiastolic = :valueDiastolic"),
    @NamedQuery(name = "BloodPressureRecord.findByCardiacfreq", query = "SELECT b FROM BloodPressureRecord b WHERE b.cardiacfreq = :cardiacfreq")})
public class BloodPressureRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "healthCareRecord_id")
    private Integer healthCareRecordid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valueSystolic")
    private int valueSystolic;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valueDiastolic")
    private int valueDiastolic;
    @Column(name = "Cardiac_freq")
    private Long cardiacfreq;
    @JoinColumn(name = "healthCareRecord_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private HealthCareRecord healthCareRecord;

    public BloodPressureRecord() {
    }

    public BloodPressureRecord(Integer healthCareRecordid) {
        this.healthCareRecordid = healthCareRecordid;
    }

    public BloodPressureRecord(Integer healthCareRecordid, int valueSystolic, int valueDiastolic) {
        this.healthCareRecordid = healthCareRecordid;
        this.valueSystolic = valueSystolic;
        this.valueDiastolic = valueDiastolic;
    }

    public Integer getHealthCareRecordid() {
        return healthCareRecordid;
    }

    public void setHealthCareRecordid(Integer healthCareRecordid) {
        this.healthCareRecordid = healthCareRecordid;
    }

    public int getValueSystolic() {
        return valueSystolic;
    }

    public void setValueSystolic(int valueSystolic) {
        this.valueSystolic = valueSystolic;
    }

    public int getValueDiastolic() {
        return valueDiastolic;
    }

    public void setValueDiastolic(int valueDiastolic) {
        this.valueDiastolic = valueDiastolic;
    }

    public Long getCardiacfreq() {
        return cardiacfreq;
    }

    public void setCardiacfreq(Long cardiacfreq) {
        this.cardiacfreq = cardiacfreq;
    }

    public HealthCareRecord getHealthCareRecord() {
        return healthCareRecord;
    }

    public void setHealthCareRecord(HealthCareRecord healthCareRecord) {
        this.healthCareRecord = healthCareRecord;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (healthCareRecordid != null ? healthCareRecordid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BloodPressureRecord)) {
            return false;
        }
        BloodPressureRecord other = (BloodPressureRecord) object;
        if ((this.healthCareRecordid == null && other.healthCareRecordid != null) || (this.healthCareRecordid != null && !this.healthCareRecordid.equals(other.healthCareRecordid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.BloodPressureRecord[ healthCareRecordid=" + healthCareRecordid + " ]";
    }
    
}
