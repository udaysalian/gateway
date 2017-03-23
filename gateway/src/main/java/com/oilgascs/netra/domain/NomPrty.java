package com.oilgascs.netra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A NomPrty.
 */
@Entity
@Table(name = "nom_prty")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NomPrty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "contr_id")
    private String contrId;

    @Column(name = "activity_nbr")
    private String activityNbr;

    @Column(name = "gas_date")
    private LocalDate gasDate;

    @Column(name = "prty_tp")
    private String prtyTp;

    @Column(name = "prty_qty")
    private Integer prtyQty;

    @Column(name = "sub_type")
    private String subType;

    @Column(name = "dir_of_flow")
    private String dirOfFlow;

    @Column(name = "updater")
    private String updater;

    @Column(name = "update_time_stamp")
    private ZonedDateTime updateTimeStamp;

    @ManyToOne
    private Nom nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContrId() {
        return contrId;
    }

    public NomPrty contrId(String contrId) {
        this.contrId = contrId;
        return this;
    }

    public void setContrId(String contrId) {
        this.contrId = contrId;
    }

    public String getActivityNbr() {
        return activityNbr;
    }

    public NomPrty activityNbr(String activityNbr) {
        this.activityNbr = activityNbr;
        return this;
    }

    public void setActivityNbr(String activityNbr) {
        this.activityNbr = activityNbr;
    }

    public LocalDate getGasDate() {
        return gasDate;
    }

    public NomPrty gasDate(LocalDate gasDate) {
        this.gasDate = gasDate;
        return this;
    }

    public void setGasDate(LocalDate gasDate) {
        this.gasDate = gasDate;
    }

    public String getPrtyTp() {
        return prtyTp;
    }

    public NomPrty prtyTp(String prtyTp) {
        this.prtyTp = prtyTp;
        return this;
    }

    public void setPrtyTp(String prtyTp) {
        this.prtyTp = prtyTp;
    }

    public Integer getPrtyQty() {
        return prtyQty;
    }

    public NomPrty prtyQty(Integer prtyQty) {
        this.prtyQty = prtyQty;
        return this;
    }

    public void setPrtyQty(Integer prtyQty) {
        this.prtyQty = prtyQty;
    }

    public String getSubType() {
        return subType;
    }

    public NomPrty subType(String subType) {
        this.subType = subType;
        return this;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDirOfFlow() {
        return dirOfFlow;
    }

    public NomPrty dirOfFlow(String dirOfFlow) {
        this.dirOfFlow = dirOfFlow;
        return this;
    }

    public void setDirOfFlow(String dirOfFlow) {
        this.dirOfFlow = dirOfFlow;
    }

    public String getUpdater() {
        return updater;
    }

    public NomPrty updater(String updater) {
        this.updater = updater;
        return this;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public ZonedDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public NomPrty updateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
        return this;
    }

    public void setUpdateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public Nom getNom() {
        return nom;
    }

    public NomPrty nom(Nom nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(Nom nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NomPrty nomPrty = (NomPrty) o;
        if (nomPrty.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, nomPrty.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NomPrty{" +
            "id=" + id +
            ", contrId='" + contrId + "'" +
            ", activityNbr='" + activityNbr + "'" +
            ", gasDate='" + gasDate + "'" +
            ", prtyTp='" + prtyTp + "'" +
            ", prtyQty='" + prtyQty + "'" +
            ", subType='" + subType + "'" +
            ", dirOfFlow='" + dirOfFlow + "'" +
            ", updater='" + updater + "'" +
            ", updateTimeStamp='" + updateTimeStamp + "'" +
            '}';
    }
}
