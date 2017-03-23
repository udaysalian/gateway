package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.BusinessAssociate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BusinessAssociate entity.
 */
@SuppressWarnings("unused")
public interface BusinessAssociateRepository extends JpaRepository<BusinessAssociate,Long> {

}
