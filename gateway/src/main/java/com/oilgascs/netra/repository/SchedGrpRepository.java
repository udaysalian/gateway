package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.SchedGrp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SchedGrp entity.
 */
@SuppressWarnings("unused")
public interface SchedGrpRepository extends JpaRepository<SchedGrp,Long> {

}
