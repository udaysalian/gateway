package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.ContrLoc;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContrLoc entity.
 */
@SuppressWarnings("unused")
public interface ContrLocRepository extends JpaRepository<ContrLoc,Long> {

}
