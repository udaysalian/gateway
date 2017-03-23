package com.oilgascs.netra.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RtSched.
 */
@Entity
@Table(name = "rt_sched")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RtSched implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rs_type")
    private String rsType;

    @Column(name = "updater")
    private String updater;

    @Column(name = "update_time_stamp")
    private ZonedDateTime updateTimeStamp;

    @OneToMany(mappedBy = "rtSched")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RtSchedVald> rtSchedValds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRsType() {
        return rsType;
    }

    public RtSched rsType(String rsType) {
        this.rsType = rsType;
        return this;
    }

    public void setRsType(String rsType) {
        this.rsType = rsType;
    }

    public String getUpdater() {
        return updater;
    }

    public RtSched updater(String updater) {
        this.updater = updater;
        return this;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public ZonedDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public RtSched updateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
        return this;
    }

    public void setUpdateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public Set<RtSchedVald> getRtSchedValds() {
        return rtSchedValds;
    }

    public RtSched rtSchedValds(Set<RtSchedVald> rtSchedValds) {
        this.rtSchedValds = rtSchedValds;
        return this;
    }

    public RtSched addRtSchedVald(RtSchedVald rtSchedVald) {
        this.rtSchedValds.add(rtSchedVald);
        rtSchedVald.setRtSched(this);
        return this;
    }

    public RtSched removeRtSchedVald(RtSchedVald rtSchedVald) {
        this.rtSchedValds.remove(rtSchedVald);
        rtSchedVald.setRtSched(null);
        return this;
    }

    public void setRtSchedValds(Set<RtSchedVald> rtSchedValds) {
        this.rtSchedValds = rtSchedValds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RtSched rtSched = (RtSched) o;
        if (rtSched.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rtSched.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RtSched{" +
            "id=" + id +
            ", rsType='" + rsType + "'" +
            ", updater='" + updater + "'" +
            ", updateTimeStamp='" + updateTimeStamp + "'" +
            '}';
    }
}
