package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.Nom;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Nom entity.
 */
@SuppressWarnings("unused")
public interface NomRepository extends JpaRepository<Nom,Long> {

}
