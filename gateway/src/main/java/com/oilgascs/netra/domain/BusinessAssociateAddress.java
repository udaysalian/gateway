package com.oilgascs.netra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BusinessAssociateAddress.
 */
@Entity
@Table(name = "business_associate_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessAssociateAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "business_associate_address_id")
    private Long businessAssociateAddressId;

    @Column(name = "add_line_1")
    private String addLine1;

    @Column(name = "address_nbr")
    private String addressNbr;

    @Column(name = "add_line_2")
    private String addLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @ManyToOne
    private BusinessAssociate businessAssociate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessAssociateAddressId() {
        return businessAssociateAddressId;
    }

    public BusinessAssociateAddress businessAssociateAddressId(Long businessAssociateAddressId) {
        this.businessAssociateAddressId = businessAssociateAddressId;
        return this;
    }

    public void setBusinessAssociateAddressId(Long businessAssociateAddressId) {
        this.businessAssociateAddressId = businessAssociateAddressId;
    }

    public String getAddLine1() {
        return addLine1;
    }

    public BusinessAssociateAddress addLine1(String addLine1) {
        this.addLine1 = addLine1;
        return this;
    }

    public void setAddLine1(String addLine1) {
        this.addLine1 = addLine1;
    }

    public String getAddressNbr() {
        return addressNbr;
    }

    public BusinessAssociateAddress addressNbr(String addressNbr) {
        this.addressNbr = addressNbr;
        return this;
    }

    public void setAddressNbr(String addressNbr) {
        this.addressNbr = addressNbr;
    }

    public String getAddLine2() {
        return addLine2;
    }

    public BusinessAssociateAddress addLine2(String addLine2) {
        this.addLine2 = addLine2;
        return this;
    }

    public void setAddLine2(String addLine2) {
        this.addLine2 = addLine2;
    }

    public String getCity() {
        return city;
    }

    public BusinessAssociateAddress city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public BusinessAssociateAddress state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public BusinessAssociateAddress zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public BusinessAssociate getBusinessAssociate() {
        return businessAssociate;
    }

    public BusinessAssociateAddress businessAssociate(BusinessAssociate businessAssociate) {
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
        BusinessAssociateAddress businessAssociateAddress = (BusinessAssociateAddress) o;
        if (businessAssociateAddress.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, businessAssociateAddress.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BusinessAssociateAddress{" +
            "id=" + id +
            ", businessAssociateAddressId='" + businessAssociateAddressId + "'" +
            ", addLine1='" + addLine1 + "'" +
            ", addressNbr='" + addressNbr + "'" +
            ", addLine2='" + addLine2 + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipCode='" + zipCode + "'" +
            '}';
    }
}
