package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.BusinessAssociateContact;

import com.oilgascs.netra.repository.BusinessAssociateContactRepository;
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
 * REST controller for managing BusinessAssociateContact.
 */
@RestController
@RequestMapping("/api")
public class BusinessAssociateContactResource {

    private final Logger log = LoggerFactory.getLogger(BusinessAssociateContactResource.class);

    private static final String ENTITY_NAME = "businessAssociateContact";
        
    private final BusinessAssociateContactRepository businessAssociateContactRepository;

    public BusinessAssociateContactResource(BusinessAssociateContactRepository businessAssociateContactRepository) {
        this.businessAssociateContactRepository = businessAssociateContactRepository;
    }

    /**
     * POST  /business-associate-contacts : Create a new businessAssociateContact.
     *
     * @param businessAssociateContact the businessAssociateContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessAssociateContact, or with status 400 (Bad Request) if the businessAssociateContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-associate-contacts")
    @Timed
    public ResponseEntity<BusinessAssociateContact> createBusinessAssociateContact(@RequestBody BusinessAssociateContact businessAssociateContact) throws URISyntaxException {
        log.debug("REST request to save BusinessAssociateContact : {}", businessAssociateContact);
        if (businessAssociateContact.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new businessAssociateContact cannot already have an ID")).body(null);
        }
        BusinessAssociateContact result = businessAssociateContactRepository.save(businessAssociateContact);
        return ResponseEntity.created(new URI("/api/business-associate-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-associate-contacts : Updates an existing businessAssociateContact.
     *
     * @param businessAssociateContact the businessAssociateContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessAssociateContact,
     * or with status 400 (Bad Request) if the businessAssociateContact is not valid,
     * or with status 500 (Internal Server Error) if the businessAssociateContact couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-associate-contacts")
    @Timed
    public ResponseEntity<BusinessAssociateContact> updateBusinessAssociateContact(@RequestBody BusinessAssociateContact businessAssociateContact) throws URISyntaxException {
        log.debug("REST request to update BusinessAssociateContact : {}", businessAssociateContact);
        if (businessAssociateContact.getId() == null) {
            return createBusinessAssociateContact(businessAssociateContact);
        }
        BusinessAssociateContact result = businessAssociateContactRepository.save(businessAssociateContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessAssociateContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-associate-contacts : get all the businessAssociateContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businessAssociateContacts in body
     */
    @GetMapping("/business-associate-contacts")
    @Timed
    public List<BusinessAssociateContact> getAllBusinessAssociateContacts() {
        log.debug("REST request to get all BusinessAssociateContacts");
        List<BusinessAssociateContact> businessAssociateContacts = businessAssociateContactRepository.findAll();
        return businessAssociateContacts;
    }

    /**
     * GET  /business-associate-contacts/:id : get the "id" businessAssociateContact.
     *
     * @param id the id of the businessAssociateContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessAssociateContact, or with status 404 (Not Found)
     */
    @GetMapping("/business-associate-contacts/{id}")
    @Timed
    public ResponseEntity<BusinessAssociateContact> getBusinessAssociateContact(@PathVariable Long id) {
        log.debug("REST request to get BusinessAssociateContact : {}", id);
        BusinessAssociateContact businessAssociateContact = businessAssociateContactRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(businessAssociateContact));
    }

    /**
     * DELETE  /business-associate-contacts/:id : delete the "id" businessAssociateContact.
     *
     * @param id the id of the businessAssociateContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-associate-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessAssociateContact(@PathVariable Long id) {
        log.debug("REST request to delete BusinessAssociateContact : {}", id);
        businessAssociateContactRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
