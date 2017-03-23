package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.SectionLocation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SectionLocation entity.
 */
@SuppressWarnings("unused")
public interface SectionLocationRepository extends JpaRepository<SectionLocation,Long> {

}
