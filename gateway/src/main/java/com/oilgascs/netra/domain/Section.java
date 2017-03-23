package com.oilgascs.netra.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Section.
 */
@Entity
@Table(name = "section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "description")
    private String description;

    @Column(name = "section_type")
    private String sectionType;

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SectionLocation> sectionLocations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public Section sectionId(Long sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getDescription() {
        return description;
    }

    public Section description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSectionType() {
        return sectionType;
    }

    public Section sectionType(String sectionType) {
        this.sectionType = sectionType;
        return this;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public Set<SectionLocation> getSectionLocations() {
        return sectionLocations;
    }

    public Section sectionLocations(Set<SectionLocation> sectionLocations) {
        this.sectionLocations = sectionLocations;
        return this;
    }

    public Section addSectionLocation(SectionLocation sectionLocation) {
        this.sectionLocations.add(sectionLocation);
        sectionLocation.setSection(this);
        return this;
    }

    public Section removeSectionLocation(SectionLocation sectionLocation) {
        this.sectionLocations.remove(sectionLocation);
        sectionLocation.setSection(null);
        return this;
    }

    public void setSectionLocations(Set<SectionLocation> sectionLocations) {
        this.sectionLocations = sectionLocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        if (section.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, section.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Section{" +
            "id=" + id +
            ", sectionId='" + sectionId + "'" +
            ", description='" + description + "'" +
            ", sectionType='" + sectionType + "'" +
            '}';
    }
}
