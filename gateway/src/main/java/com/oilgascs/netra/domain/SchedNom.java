package com.oilgascs.netra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SchedNom.
 */
@Entity
@Table(name = "sched_nom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchedNom implements Serializable {

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

    @Column(name = "old_schd_rcpt_qty")
    private Integer oldSchdRcptQty;

    @Column(name = "new_schd_rcpt_qty")
    private Integer newSchdRcptQty;

    @Column(name = "old_schd_dlvry_qty")
    private Integer oldSchdDlvryQty;

    @Column(name = "new_schd_dlvry_qty")
    private Integer newSchdDlvryQty;

    @Column(name = "upater")
    private String upater;

    @Column(name = "update_time_stamp")
    private ZonedDateTime updateTimeStamp;

    @OneToOne
    @JoinColumn(unique = true)
    private SchedEvent schedEvent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContrId() {
        return contrId;
    }

    public SchedNom contrId(String contrId) {
        this.contrId = contrId;
        return this;
    }

    public void setContrId(String contrId) {
        this.contrId = contrId;
    }

    public String getActivityNbr() {
        return activityNbr;
    }

    public SchedNom activityNbr(String activityNbr) {
        this.activityNbr = activityNbr;
        return this;
    }

    public void setActivityNbr(String activityNbr) {
        this.activityNbr = activityNbr;
    }

    public LocalDate getGasDate() {
        return gasDate;
    }

    public SchedNom gasDate(LocalDate gasDate) {
        this.gasDate = gasDate;
        return this;
    }

    public void setGasDate(LocalDate gasDate) {
        this.gasDate = gasDate;
    }

    public Integer getOldSchdRcptQty() {
        return oldSchdRcptQty;
    }

    public SchedNom oldSchdRcptQty(Integer oldSchdRcptQty) {
        this.oldSchdRcptQty = oldSchdRcptQty;
        return this;
    }

    public void setOldSchdRcptQty(Integer oldSchdRcptQty) {
        this.oldSchdRcptQty = oldSchdRcptQty;
    }

    public Integer getNewSchdRcptQty() {
        return newSchdRcptQty;
    }

    public SchedNom newSchdRcptQty(Integer newSchdRcptQty) {
        this.newSchdRcptQty = newSchdRcptQty;
        return this;
    }

    public void setNewSchdRcptQty(Integer newSchdRcptQty) {
        this.newSchdRcptQty = newSchdRcptQty;
    }

    public Integer getOldSchdDlvryQty() {
        return oldSchdDlvryQty;
    }

    public SchedNom oldSchdDlvryQty(Integer oldSchdDlvryQty) {
        this.oldSchdDlvryQty = oldSchdDlvryQty;
        return this;
    }

    public void setOldSchdDlvryQty(Integer oldSchdDlvryQty) {
        this.oldSchdDlvryQty = oldSchdDlvryQty;
    }

    public Integer getNewSchdDlvryQty() {
        return newSchdDlvryQty;
    }

    public SchedNom newSchdDlvryQty(Integer newSchdDlvryQty) {
        this.newSchdDlvryQty = newSchdDlvryQty;
        return this;
    }

    public void setNewSchdDlvryQty(Integer newSchdDlvryQty) {
        this.newSchdDlvryQty = newSchdDlvryQty;
    }

    public String getUpater() {
        return upater;
    }

    public SchedNom upater(String upater) {
        this.upater = upater;
        return this;
    }

    public void setUpater(String upater) {
        this.upater = upater;
    }

    public ZonedDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public SchedNom updateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
        return this;
    }

    public void setUpdateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public SchedEvent getSchedEvent() {
        return schedEvent;
    }

    public SchedNom schedEvent(SchedEvent schedEvent) {
        this.schedEvent = schedEvent;
        return this;
    }

    public void setSchedEvent(SchedEvent schedEvent) {
        this.schedEvent = schedEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchedNom schedNom = (SchedNom) o;
        if (schedNom.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schedNom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchedNom{" +
            "id=" + id +
            ", contrId='" + contrId + "'" +
            ", activityNbr='" + activityNbr + "'" +
            ", gasDate='" + gasDate + "'" +
            ", oldSchdRcptQty='" + oldSchdRcptQty + "'" +
            ", newSchdRcptQty='" + newSchdRcptQty + "'" +
            ", oldSchdDlvryQty='" + oldSchdDlvryQty + "'" +
            ", newSchdDlvryQty='" + newSchdDlvryQty + "'" +
            ", upater='" + upater + "'" +
            ", updateTimeStamp='" + updateTimeStamp + "'" +
            '}';
    }
}
