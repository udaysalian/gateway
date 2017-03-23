package com.oilgascs.netra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "contr_id")
    private String contrId;

    @Column(name = "activity_nbr")
    private String activityNbr;

    @Column(name = "rcpt_loc_nbr")
    private String rcptLocNbr;

    @Column(name = "dlvry_loc_nbr")
    private String dlvryLocNbr;

    @Column(name = "updater")
    private String updater;

    @Column(name = "update_time_stamp")
    private ZonedDateTime updateTimeStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContrId() {
        return contrId;
    }

    public Activity contrId(String contrId) {
        this.contrId = contrId;
        return this;
    }

    public void setContrId(String contrId) {
        this.contrId = contrId;
    }

    public String getActivityNbr() {
        return activityNbr;
    }

    public Activity activityNbr(String activityNbr) {
        this.activityNbr = activityNbr;
        return this;
    }

    public void setActivityNbr(String activityNbr) {
        this.activityNbr = activityNbr;
    }

    public String getRcptLocNbr() {
        return rcptLocNbr;
    }

    public Activity rcptLocNbr(String rcptLocNbr) {
        this.rcptLocNbr = rcptLocNbr;
        return this;
    }

    public void setRcptLocNbr(String rcptLocNbr) {
        this.rcptLocNbr = rcptLocNbr;
    }

    public String getDlvryLocNbr() {
        return dlvryLocNbr;
    }

    public Activity dlvryLocNbr(String dlvryLocNbr) {
        this.dlvryLocNbr = dlvryLocNbr;
        return this;
    }

    public void setDlvryLocNbr(String dlvryLocNbr) {
        this.dlvryLocNbr = dlvryLocNbr;
    }

    public String getUpdater() {
        return updater;
    }

    public Activity updater(String updater) {
        this.updater = updater;
        return this;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public ZonedDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public Activity updateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
        return this;
    }

    public void setUpdateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if (activity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + id +
            ", contrId='" + contrId + "'" +
            ", activityNbr='" + activityNbr + "'" +
            ", rcptLocNbr='" + rcptLocNbr + "'" +
            ", dlvryLocNbr='" + dlvryLocNbr + "'" +
            ", updater='" + updater + "'" +
            ", updateTimeStamp='" + updateTimeStamp + "'" +
            '}';
    }
}
