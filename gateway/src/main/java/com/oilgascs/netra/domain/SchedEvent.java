package com.oilgascs.netra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SchedEvent.
 */
@Entity
@Table(name = "sched_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "working_capacity")
    private Integer workingCapacity;

    @Column(name = "adj_working_capacity")
    private Integer adjWorkingCapacity;

    @Column(name = "status")
    private String status;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "updater")
    private String updater;

    @Column(name = "update_timestamp")
    private ZonedDateTime updateTimestamp;

    @OneToOne
    @JoinColumn(unique = true)
    private SchedGrp schedGrp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public SchedEvent eventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getWorkingCapacity() {
        return workingCapacity;
    }

    public SchedEvent workingCapacity(Integer workingCapacity) {
        this.workingCapacity = workingCapacity;
        return this;
    }

    public void setWorkingCapacity(Integer workingCapacity) {
        this.workingCapacity = workingCapacity;
    }

    public Integer getAdjWorkingCapacity() {
        return adjWorkingCapacity;
    }

    public SchedEvent adjWorkingCapacity(Integer adjWorkingCapacity) {
        this.adjWorkingCapacity = adjWorkingCapacity;
        return this;
    }

    public void setAdjWorkingCapacity(Integer adjWorkingCapacity) {
        this.adjWorkingCapacity = adjWorkingCapacity;
    }

    public String getStatus() {
        return status;
    }

    public SchedEvent status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventType() {
        return eventType;
    }

    public SchedEvent eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getUpdater() {
        return updater;
    }

    public SchedEvent updater(String updater) {
        this.updater = updater;
        return this;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public ZonedDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public SchedEvent updateTimestamp(ZonedDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
        return this;
    }

    public void setUpdateTimestamp(ZonedDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public SchedGrp getSchedGrp() {
        return schedGrp;
    }

    public SchedEvent schedGrp(SchedGrp schedGrp) {
        this.schedGrp = schedGrp;
        return this;
    }

    public void setSchedGrp(SchedGrp schedGrp) {
        this.schedGrp = schedGrp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchedEvent schedEvent = (SchedEvent) o;
        if (schedEvent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schedEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchedEvent{" +
            "id=" + id +
            ", eventId='" + eventId + "'" +
            ", workingCapacity='" + workingCapacity + "'" +
            ", adjWorkingCapacity='" + adjWorkingCapacity + "'" +
            ", status='" + status + "'" +
            ", eventType='" + eventType + "'" +
            ", updater='" + updater + "'" +
            ", updateTimestamp='" + updateTimestamp + "'" +
            '}';
    }
}
