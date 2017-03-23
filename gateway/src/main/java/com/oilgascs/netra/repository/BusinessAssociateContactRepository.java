package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.BusinessAssociateContact;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BusinessAssociateContact entity.
 */
@SuppressWarnings("unused")
public interface BusinessAssociateContactRepository extends JpaRepository<BusinessAssociateContact,Long> {

}
