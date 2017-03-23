package com.oilgascs.netra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SchedGrp.
 */
@Entity
@Table(name = "sched_grp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchedGrp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sched_grp_id")
    private Long schedGrpId;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private Section rcptSection;

    @OneToOne
    @JoinColumn(unique = true)
    private Section dlvrySection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchedGrpId() {
        return schedGrpId;
    }

    public SchedGrp schedGrpId(Long schedGrpId) {
        this.schedGrpId = schedGrpId;
        return this;
    }

    public void setSchedGrpId(Long schedGrpId) {
        this.schedGrpId = schedGrpId;
    }

    public String getDescription() {
        return description;
    }

    public SchedGrp description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Section getRcptSection() {
        return rcptSection;
    }

    public SchedGrp rcptSection(Section section) {
        this.rcptSection = section;
        return this;
    }

    public void setRcptSection(Section section) {
        this.rcptSection = section;
    }

    public Section getDlvrySection() {
        return dlvrySection;
    }

    public SchedGrp dlvrySection(Section section) {
        this.dlvrySection = section;
        return this;
    }

    public void setDlvrySection(Section section) {
        this.dlvrySection = section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchedGrp schedGrp = (SchedGrp) o;
        if (schedGrp.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schedGrp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchedGrp{" +
            "id=" + id +
            ", schedGrpId='" + schedGrpId + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
