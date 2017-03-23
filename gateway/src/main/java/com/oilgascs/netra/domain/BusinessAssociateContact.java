package com.oilgascs.netra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A BusinessAssociateContact.
 */
@Entity
@Table(name = "business_associate_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessAssociateContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "business_associate_contact_id")
    private Long businessAssociateContactId;

    @Column(name = "begin_date")
    private LocalDate beginDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    private BusinessAssociate businessAssociate;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contact;

    @OneToOne
    @JoinColumn(unique = true)
    private BusinessAssociateAddress mailAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private BusinessAssociateAddress deliveryAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessAssociateContactId() {
        return businessAssociateContactId;
    }

    public BusinessAssociateContact businessAssociateContactId(Long businessAssociateContactId) {
        this.businessAssociateContactId = businessAssociateContactId;
        return this;
    }

    public void setBusinessAssociateContactId(Long businessAssociateContactId) {
        this.businessAssociateContactId = businessAssociateContactId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public BusinessAssociateContact beginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BusinessAssociateContact endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BusinessAssociate getBusinessAssociate() {
        return businessAssociate;
    }

    public BusinessAssociateContact businessAssociate(BusinessAssociate businessAssociate) {
        this.businessAssociate = businessAssociate;
        return this;
    }

    public void setBusinessAssociate(BusinessAssociate businessAssociate) {
        this.businessAssociate = businessAssociate;
    }

    public Contact getContact() {
        return contact;
    }

    public BusinessAssociateContact contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public BusinessAssociateAddress getMailAddress() {
        return mailAddress;
    }

    public BusinessAssociateContact mailAddress(BusinessAssociateAddress businessAssociateAddress) {
        this.mailAddress = businessAssociateAddress;
        return this;
    }

    public void setMailAddress(BusinessAssociateAddress businessAssociateAddress) {
        this.mailAddress = businessAssociateAddress;
    }

    public BusinessAssociateAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public BusinessAssociateContact deliveryAddress(BusinessAssociateAddress businessAssociateAddress) {
        this.deliveryAddress = businessAssociateAddress;
        return this;
    }

    public void setDeliveryAddress(BusinessAssociateAddress businessAssociateAddress) {
        this.deliveryAddress = businessAssociateAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessAssociateContact businessAssociateContact = (BusinessAssociateContact) o;
        if (businessAssociateContact.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, businessAssociateContact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BusinessAssociateContact{" +
            "id=" + id +
            ", businessAssociateContactId='" + businessAssociateContactId + "'" +
            ", beginDate='" + beginDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
