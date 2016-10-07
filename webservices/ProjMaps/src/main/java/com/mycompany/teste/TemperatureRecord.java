/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.teste;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "temperatureRecord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TemperatureRecord.findAll", query = "SELECT t FROM TemperatureRecord t"),
    @NamedQuery(name = "TemperatureRecord.findByHealthCareRecordid", query = "SELECT t FROM TemperatureRecord t WHERE t.healthCareRecordid = :healthCareRecordid"),
    @NamedQuery(name = "TemperatureRecord.findByValueCelcius", query = "SELECT t FROM TemperatureRecord t WHERE t.valueCelcius = :valueCelcius")})
public class TemperatureRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "healthCareRecord_id")
    private Integer healthCareRecordid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "value_celcius")
    private BigDecimal valueCelcius;
    @JoinColumn(name = "healthCareRecord_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private HealthCareRecord healthCareRecord;

    public TemperatureRecord() {
    }

    public TemperatureRecord(Integer healthCareRecordid) {
        this.healthCareRecordid = healthCareRecordid;
    }

    public TemperatureRecord(Integer healthCareRecordid, BigDecimal valueCelcius) {
        this.healthCareRecordid = healthCareRecordid;
        this.valueCelcius = valueCelcius;
    }

    public Integer getHealthCareRecordid() {
        return healthCareRecordid;
    }

    public void setHealthCareRecordid(Integer healthCareRecordid) {
        this.healthCareRecordid = healthCareRecordid;
    }

    public BigDecimal getValueCelcius() {
        return valueCelcius;
    }

    public void setValueCelcius(BigDecimal valueCelcius) {
        this.valueCelcius = valueCelcius;
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
        if (!(object instanceof TemperatureRecord)) {
            return false;
        }
        TemperatureRecord other = (TemperatureRecord) object;
        if ((this.healthCareRecordid == null && other.healthCareRecordid != null) || (this.healthCareRecordid != null && !this.healthCareRecordid.equals(other.healthCareRecordid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.TemperatureRecord[ healthCareRecordid=" + healthCareRecordid + " ]";
    }
    
}
