package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.NomPrty;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NomPrty entity.
 */
@SuppressWarnings("unused")
public interface NomPrtyRepository extends JpaRepository<NomPrty,Long> {

}
