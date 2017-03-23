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
 * A BusinessAssociate.
 */
@Entity
@Table(name = "business_associate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessAssociate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "business_associate_id")
    private Long businessAssociateId;

    @Column(name = "ba_name")
    private String baName;

    @Column(name = "ba_abbr")
    private String baAbbr;

    @Column(name = "ba_nbr")
    private String baNbr;

    @Column(name = "ba_duns_nbr")
    private String baDunsNbr;

    @OneToMany(mappedBy = "businessAssociate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessAssociateContact> businessAssociateContacts = new HashSet<>();

    @OneToMany(mappedBy = "businessAssociate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessAssociateAddress> businessAssociateAddresses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessAssociateId() {
        return businessAssociateId;
    }

    public BusinessAssociate businessAssociateId(Long businessAssociateId) {
        this.businessAssociateId = businessAssociateId;
        return this;
    }

    public void setBusinessAssociateId(Long businessAssociateId) {
        this.businessAssociateId = businessAssociateId;
    }

    public String getBaName() {
        return baName;
    }

    public BusinessAssociate baName(String baName) {
        this.baName = baName;
        return this;
    }

    public void setBaName(String baName) {
        this.baName = baName;
    }

    public String getBaAbbr() {
        return baAbbr;
    }

    public BusinessAssociate baAbbr(String baAbbr) {
        this.baAbbr = baAbbr;
        return this;
    }

    public void setBaAbbr(String baAbbr) {
        this.baAbbr = baAbbr;
    }

    public String getBaNbr() {
        return baNbr;
    }

    public BusinessAssociate baNbr(String baNbr) {
        this.baNbr = baNbr;
        return this;
    }

    public void setBaNbr(String baNbr) {
        this.baNbr = baNbr;
    }

    public String getBaDunsNbr() {
        return baDunsNbr;
    }

    public BusinessAssociate baDunsNbr(String baDunsNbr) {
        this.baDunsNbr = baDunsNbr;
        return this;
    }

    public void setBaDunsNbr(String baDunsNbr) {
        this.baDunsNbr = baDunsNbr;
    }

    public Set<BusinessAssociateContact> getBusinessAssociateContacts() {
        return businessAssociateContacts;
    }

    public BusinessAssociate businessAssociateContacts(Set<BusinessAssociateContact> businessAssociateContacts) {
        this.businessAssociateContacts = businessAssociateContacts;
        return this;
    }

    public BusinessAssociate addBusinessAssociateContact(BusinessAssociateContact businessAssociateContact) {
        this.businessAssociateContacts.add(businessAssociateContact);
        businessAssociateContact.setBusinessAssociate(this);
        return this;
    }

    public BusinessAssociate removeBusinessAssociateContact(BusinessAssociateContact businessAssociateContact) {
        this.businessAssociateContacts.remove(businessAssociateContact);
        businessAssociateContact.setBusinessAssociate(null);
        return this;
    }

    public void setBusinessAssociateContacts(Set<BusinessAssociateContact> businessAssociateContacts) {
        this.businessAssociateContacts = businessAssociateContacts;
    }

    public Set<BusinessAssociateAddress> getBusinessAssociateAddresses() {
        return businessAssociateAddresses;
    }

    public BusinessAssociate businessAssociateAddresses(Set<BusinessAssociateAddress> businessAssociateAddresses) {
        this.businessAssociateAddresses = businessAssociateAddresses;
        return this;
    }

    public BusinessAssociate addBusinessAssociateAddress(BusinessAssociateAddress businessAssociateAddress) {
        this.businessAssociateAddresses.add(businessAssociateAddress);
        businessAssociateAddress.setBusinessAssociate(this);
        return this;
    }

    public BusinessAssociate removeBusinessAssociateAddress(BusinessAssociateAddress businessAssociateAddress) {
        this.businessAssociateAddresses.remove(businessAssociateAddress);
        businessAssociateAddress.setBusinessAssociate(null);
        return this;
    }

    public void setBusinessAssociateAddresses(Set<BusinessAssociateAddress> businessAssociateAddresses) {
        this.businessAssociateAddresses = businessAssociateAddresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessAssociate businessAssociate = (BusinessAssociate) o;
        if (businessAssociate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, businessAssociate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BusinessAssociate{" +
            "id=" + id +
            ", businessAssociateId='" + businessAssociateId + "'" +
            ", baName='" + baName + "'" +
            ", baAbbr='" + baAbbr + "'" +
            ", baNbr='" + baNbr + "'" +
            ", baDunsNbr='" + baDunsNbr + "'" +
            '}';
    }
}
