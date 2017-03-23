package com.oilgascs.netra.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Nom.
 */
@Entity
@Table(name = "nom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "activity_nbr")
    private String activityNbr;

    @Column(name = "gas_date")
    private LocalDate gasDate;

    @Column(name = "req_rcpt_qty")
    private Integer reqRcptQty;

    @Column(name = "req_dlvry_qty")
    private Integer reqDlvryQty;

    @Column(name = "old_schd_rcpt_qty")
    private Integer oldSchdRcptQty;

    @Column(name = "new_schd_rcpt_qty")
    private Integer newSchdRcptQty;

    @Column(name = "old_schd_dlvry_qty")
    private Integer oldSchdDlvryQty;

    @Column(name = "new_schd_dlvry_qty")
    private Integer newSchdDlvryQty;

    @Column(name = "updater")
    private String updater;

    @Column(name = "update_time_stamp")
    private ZonedDateTime updateTimeStamp;

    @OneToOne
    @JoinColumn(unique = true)
    private Activity activity;

    @OneToOne
    @JoinColumn(unique = true)
    private Contract contr;

    @OneToMany(mappedBy = "nom")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NomPrty> prties = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityNbr() {
        return activityNbr;
    }

    public Nom activityNbr(String activityNbr) {
        this.activityNbr = activityNbr;
        return this;
    }

    public void setActivityNbr(String activityNbr) {
        this.activityNbr = activityNbr;
    }

    public LocalDate getGasDate() {
        return gasDate;
    }

    public Nom gasDate(LocalDate gasDate) {
        this.gasDate = gasDate;
        return this;
    }

    public void setGasDate(LocalDate gasDate) {
        this.gasDate = gasDate;
    }

    public Integer getReqRcptQty() {
        return reqRcptQty;
    }

    public Nom reqRcptQty(Integer reqRcptQty) {
        this.reqRcptQty = reqRcptQty;
        return this;
    }

    public void setReqRcptQty(Integer reqRcptQty) {
        this.reqRcptQty = reqRcptQty;
    }

    public Integer getReqDlvryQty() {
        return reqDlvryQty;
    }

    public Nom reqDlvryQty(Integer reqDlvryQty) {
        this.reqDlvryQty = reqDlvryQty;
        return this;
    }

    public void setReqDlvryQty(Integer reqDlvryQty) {
        this.reqDlvryQty = reqDlvryQty;
    }

    public Integer getOldSchdRcptQty() {
        return oldSchdRcptQty;
    }

    public Nom oldSchdRcptQty(Integer oldSchdRcptQty) {
        this.oldSchdRcptQty = oldSchdRcptQty;
        return this;
    }

    public void setOldSchdRcptQty(Integer oldSchdRcptQty) {
        this.oldSchdRcptQty = oldSchdRcptQty;
    }

    public Integer getNewSchdRcptQty() {
        return newSchdRcptQty;
    }

    public Nom newSchdRcptQty(Integer newSchdRcptQty) {
        this.newSchdRcptQty = newSchdRcptQty;
        return this;
    }

    public void setNewSchdRcptQty(Integer newSchdRcptQty) {
        this.newSchdRcptQty = newSchdRcptQty;
    }

    public Integer getOldSchdDlvryQty() {
        return oldSchdDlvryQty;
    }

    public Nom oldSchdDlvryQty(Integer oldSchdDlvryQty) {
        this.oldSchdDlvryQty = oldSchdDlvryQty;
        return this;
    }

    public void setOldSchdDlvryQty(Integer oldSchdDlvryQty) {
        this.oldSchdDlvryQty = oldSchdDlvryQty;
    }

    public Integer getNewSchdDlvryQty() {
        return newSchdDlvryQty;
    }

    public Nom newSchdDlvryQty(Integer newSchdDlvryQty) {
        this.newSchdDlvryQty = newSchdDlvryQty;
        return this;
    }

    public void setNewSchdDlvryQty(Integer newSchdDlvryQty) {
        this.newSchdDlvryQty = newSchdDlvryQty;
    }

    public String getUpdater() {
        return updater;
    }

    public Nom updater(String updater) {
        this.updater = updater;
        return this;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public ZonedDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public Nom updateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
        return this;
    }

    public void setUpdateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public Activity getActivity() {
        return activity;
    }

    public Nom activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Contract getContr() {
        return contr;
    }

    public Nom contr(Contract contract) {
        this.contr = contract;
        return this;
    }

    public void setContr(Contract contract) {
        this.contr = contract;
    }

    public Set<NomPrty> getPrties() {
        return prties;
    }

    public Nom prties(Set<NomPrty> nomPrties) {
        this.prties = nomPrties;
        return this;
    }

    public Nom addPrty(NomPrty nomPrty) {
        this.prties.add(nomPrty);
        nomPrty.setNom(this);
        return this;
    }

    public Nom removePrty(NomPrty nomPrty) {
        this.prties.remove(nomPrty);
        nomPrty.setNom(null);
        return this;
    }

    public void setPrties(Set<NomPrty> nomPrties) {
        this.prties = nomPrties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nom nom = (Nom) o;
        if (nom.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, nom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Nom{" +
            "id=" + id +
            ", activityNbr='" + activityNbr + "'" +
            ", gasDate='" + gasDate + "'" +
            ", reqRcptQty='" + reqRcptQty + "'" +
            ", reqDlvryQty='" + reqDlvryQty + "'" +
            ", oldSchdRcptQty='" + oldSchdRcptQty + "'" +
            ", newSchdRcptQty='" + newSchdRcptQty + "'" +
            ", oldSchdDlvryQty='" + oldSchdDlvryQty + "'" +
            ", newSchdDlvryQty='" + newSchdDlvryQty + "'" +
            ", updater='" + updater + "'" +
            ", updateTimeStamp='" + updateTimeStamp + "'" +
            '}';
    }
}
