package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.SchedEvent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SchedEvent entity.
 */
@SuppressWarnings("unused")
public interface SchedEventRepository extends JpaRepository<SchedEvent,Long> {

}
