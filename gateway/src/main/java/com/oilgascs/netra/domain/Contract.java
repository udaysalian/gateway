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
 * A Contract.
 */
@Entity
@Table(name = "contract")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "contr_id")
    private String contrId;

    @Column(name = "updater")
    private String updater;

    @Column(name = "update_time_stamp")
    private ZonedDateTime updateTimeStamp;

    @OneToOne
    @JoinColumn(unique = true)
    private RtSched rtSched;

    @OneToMany(mappedBy = "contract")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContrLoc> contrLocs = new HashSet<>();

    @ManyToOne
    private BusinessAssociate businessAssociate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContrId() {
        return contrId;
    }

    public Contract contrId(String contrId) {
        this.contrId = contrId;
        return this;
    }

    public void setContrId(String contrId) {
        this.contrId = contrId;
    }

    public String getUpdater() {
        return updater;
    }

    public Contract updater(String updater) {
        this.updater = updater;
        return this;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public ZonedDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public Contract updateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
        return this;
    }

    public void setUpdateTimeStamp(ZonedDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public RtSched getRtSched() {
        return rtSched;
    }

    public Contract rtSched(RtSched rtSched) {
        this.rtSched = rtSched;
        return this;
    }

    public void setRtSched(RtSched rtSched) {
        this.rtSched = rtSched;
    }

    public Set<ContrLoc> getContrLocs() {
        return contrLocs;
    }

    public Contract contrLocs(Set<ContrLoc> contrLocs) {
        this.contrLocs = contrLocs;
        return this;
    }

    public Contract addContrLoc(ContrLoc contrLoc) {
        this.contrLocs.add(contrLoc);
        contrLoc.setContract(this);
        return this;
    }

    public Contract removeContrLoc(ContrLoc contrLoc) {
        this.contrLocs.remove(contrLoc);
        contrLoc.setContract(null);
        return this;
    }

    public void setContrLocs(Set<ContrLoc> contrLocs) {
        this.contrLocs = contrLocs;
    }

    public BusinessAssociate getBusinessAssociate() {
        return businessAssociate;
    }

    public Contract businessAssociate(BusinessAssociate businessAssociate) {
        this.businessAssociate = businessAssociate;
        return this;
    }

    public void setBusinessAssociate(BusinessAssociate businessAssociate) {
        this.businessAssociate = businessAssociate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contract contract = (Contract) o;
        if (contract.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contract.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contract{" +
            "id=" + id +
            ", contrId='" + contrId + "'" +
            ", updater='" + updater + "'" +
            ", updateTimeStamp='" + updateTimeStamp + "'" +
            '}';
    }
}
