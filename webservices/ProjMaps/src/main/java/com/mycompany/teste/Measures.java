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
@Table(name = "measures")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Measures.findAll", query = "SELECT m FROM Measures m"),
    @NamedQuery(name = "Measures.findById", query = "SELECT m FROM Measures m WHERE m.id = :id"),
    @NamedQuery(name = "Measures.findByMeasureType", query = "SELECT m FROM Measures m WHERE m.measureType = :measureType"),
    @NamedQuery(name = "Measures.findByMeasureTakeTime", query = "SELECT m FROM Measures m WHERE m.measureTakeTime = :measureTakeTime"),
    @NamedQuery(name = "Measures.findByMeasureHowLong", query = "SELECT m FROM Measures m WHERE m.measureHowLong = :measureHowLong")})
public class Measures implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "measure_type")
    private String measureType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "measure_take_time")
    private String measureTakeTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "measure_how_long")
    private String measureHowLong;
    @JoinColumn(name = "prescription_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prescription prescriptionId;

    public Measures() {
    }

    public Measures(Integer id) {
        this.id = id;
    }

    public Measures(Integer id, String measureType, String measureTakeTime, String measureHowLong) {
        this.id = id;
        this.measureType = measureType;
        this.measureTakeTime = measureTakeTime;
        this.measureHowLong = measureHowLong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public String getMeasureTakeTime() {
        return measureTakeTime;
    }

    public void setMeasureTakeTime(String measureTakeTime) {
        this.measureTakeTime = measureTakeTime;
    }

    public String getMeasureHowLong() {
        return measureHowLong;
    }

    public void setMeasureHowLong(String measureHowLong) {
        this.measureHowLong = measureHowLong;
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
        if (!(object instanceof Measures)) {
            return false;
        }
        Measures other = (Measures) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.teste.Measures[ id=" + id + " ]";
    }
    
}
