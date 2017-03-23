package com.oilgascs.netra.repository;

import com.oilgascs.netra.domain.Contract;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contract entity.
 */
@SuppressWarnings("unused")
public interface ContractRepository extends JpaRepository<Contract,Long> {

}
