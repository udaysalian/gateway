package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.BusinessAssociateAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BusinessAssociateAddress entity.
 */
@SuppressWarnings("unused")
public interface BusinessAssociateAddressRepository extends JpaRepository<BusinessAssociateAddress,Long> {

}
