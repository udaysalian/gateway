package com.oilgascs.netra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RtSchedVald.
 */
@Entity
@Table(name = "rt_sched_vald")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RtSchedVald implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valid_type")
    private String validType;

    @Column(name = "updater")
    private String updater;

    @Column(name = "update_time_stamp")
    private ZonedDateTime updateTimeStamp;

    @ManyToOne
    private RtSched rtSched;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValidType() {
        return validType;
    }

    public RtSchedVald validType(String validType) {
        this.validType = validType;
        return this;
    }

    public void setValidType(String validType) {
        this.validType = validType;
    }

    public String getUpdater() {
        return updater;
    }

    public RtSchedVald updater(String updater) {
        this.updater = updater;
        return this;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public ZonedDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public RtSchedVald updateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
        return this;
    }

    public void setUpdateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public RtSched getRtSched() {
        return rtSched;
    }

    public RtSchedVald rtSched(RtSched rtSched) {
        this.rtSched = rtSched;
        return this;
    }

    public void setRtSched(RtSched rtSched) {
        this.rtSched = rtSched;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RtSchedVald rtSchedVald = (RtSchedVald) o;
        if (rtSchedVald.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rtSchedVald.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RtSchedVald{" +
            "id=" + id +
            ", validType='" + validType + "'" +
            ", updater='" + updater + "'" +
            ", updateTimeStamp='" + updateTimeStamp + "'" +
            '}';
    }
}
