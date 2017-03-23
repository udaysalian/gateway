package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.Section;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Section entity.
 */
@SuppressWarnings("unused")
public interface SectionRepository extends JpaRepository<Section,Long> {

}
