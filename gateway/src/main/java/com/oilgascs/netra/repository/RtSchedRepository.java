package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.RtSched;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RtSched entity.
 */
@SuppressWarnings("unused")
public interface RtSchedRepository extends JpaRepository<RtSched,Long> {

}
