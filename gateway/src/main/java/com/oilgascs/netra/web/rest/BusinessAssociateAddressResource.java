package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.BusinessAssociateAddress;

import com.oilgascs.netra.repository.BusinessAssociateAddressRepository;
import com.oilgascs.netra.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BusinessAssociateAddress.
 */
@RestController
@RequestMapping("/api")
public class BusinessAssociateAddressResource {

    private final Logger log = LoggerFactory.getLogger(BusinessAssociateAddressResource.class);

    private static final String ENTITY_NAME = "businessAssociateAddress";
        
    private final BusinessAssociateAddressRepository businessAssociateAddressRepository;

    public BusinessAssociateAddressResource(BusinessAssociateAddressRepository businessAssociateAddressRepository) {
        this.businessAssociateAddressRepository = businessAssociateAddressRepository;
    }

    /**
     * POST  /business-associate-addresses : Create a new businessAssociateAddress.
     *
     * @param businessAssociateAddress the businessAssociateAddress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessAssociateAddress, or with status 400 (Bad Request) if the businessAssociateAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-associate-addresses")
    @Timed
    public ResponseEntity<BusinessAssociateAddress> createBusinessAssociateAddress(@RequestBody BusinessAssociateAddress businessAssociateAddress) throws URISyntaxException {
        log.debug("REST request to save BusinessAssociateAddress : {}", businessAssociateAddress);
        if (businessAssociateAddress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new businessAssociateAddress cannot already have an ID")).body(null);
        }
        BusinessAssociateAddress result = businessAssociateAddressRepository.save(businessAssociateAddress);
        return ResponseEntity.created(new URI("/api/business-associate-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-associate-addresses : Updates an existing businessAssociateAddress.
     *
     * @param businessAssociateAddress the businessAssociateAddress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessAssociateAddress,
     * or with status 400 (Bad Request) if the businessAssociateAddress is not valid,
     * or with status 500 (Internal Server Error) if the businessAssociateAddress couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-associate-addresses")
    @Timed
    public ResponseEntity<BusinessAssociateAddress> updateBusinessAssociateAddress(@RequestBody BusinessAssociateAddress businessAssociateAddress) throws URISyntaxException {
        log.debug("REST request to update BusinessAssociateAddress : {}", businessAssociateAddress);
        if (businessAssociateAddress.getId() == null) {
            return createBusinessAssociateAddress(businessAssociateAddress);
        }
        BusinessAssociateAddress result = businessAssociateAddressRepository.save(businessAssociateAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessAssociateAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-associate-addresses : get all the businessAssociateAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businessAssociateAddresses in body
     */
    @GetMapping("/business-associate-addresses")
    @Timed
    public List<BusinessAssociateAddress> getAllBusinessAssociateAddresses() {
        log.debug("REST request to get all BusinessAssociateAddresses");
        List<BusinessAssociateAddress> businessAssociateAddresses = businessAssociateAddressRepository.findAll();
        return businessAssociateAddresses;
    }

    /**
     * GET  /business-associate-addresses/:id : get the "id" businessAssociateAddress.
     *
     * @param id the id of the businessAssociateAddress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessAssociateAddress, or with status 404 (Not Found)
     */
    @GetMapping("/business-associate-addresses/{id}")
    @Timed
    public ResponseEntity<BusinessAssociateAddress> getBusinessAssociateAddress(@PathVariable Long id) {
        log.debug("REST request to get BusinessAssociateAddress : {}", id);
        BusinessAssociateAddress businessAssociateAddress = businessAssociateAddressRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(businessAssociateAddress));
    }

    /**
     * DELETE  /business-associate-addresses/:id : delete the "id" businessAssociateAddress.
     *
     * @param id the id of the businessAssociateAddress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-associate-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessAssociateAddress(@PathVariable Long id) {
        log.debug("REST request to delete BusinessAssociateAddress : {}", id);
        businessAssociateAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
