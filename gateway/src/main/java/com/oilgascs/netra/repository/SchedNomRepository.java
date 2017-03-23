package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.SchedNom;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SchedNom entity.
 */
@SuppressWarnings("unused")
public interface SchedNomRepository extends JpaRepository<SchedNom,Long> {

}
