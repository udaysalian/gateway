package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.BusinessAssociate;

import com.oilgascs.netra.repository.BusinessAssociateRepository;
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
 * REST controller for managing BusinessAssociate.
 */
@RestController
@RequestMapping("/api")
public class BusinessAssociateResource {

    private final Logger log = LoggerFactory.getLogger(BusinessAssociateResource.class);

    private static final String ENTITY_NAME = "businessAssociate";
        
    private final BusinessAssociateRepository businessAssociateRepository;

    public BusinessAssociateResource(BusinessAssociateRepository businessAssociateRepository) {
        this.businessAssociateRepository = businessAssociateRepository;
    }

    /**
     * POST  /business-associates : Create a new businessAssociate.
     *
     * @param businessAssociate the businessAssociate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessAssociate, or with status 400 (Bad Request) if the businessAssociate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-associates")
    @Timed
    public ResponseEntity<BusinessAssociate> createBusinessAssociate(@RequestBody BusinessAssociate businessAssociate) throws URISyntaxException {
        log.debug("REST request to save BusinessAssociate : {}", businessAssociate);
        if (businessAssociate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new businessAssociate cannot already have an ID")).body(null);
        }
        BusinessAssociate result = businessAssociateRepository.save(businessAssociate);
        return ResponseEntity.created(new URI("/api/business-associates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-associates : Updates an existing businessAssociate.
     *
     * @param businessAssociate the businessAssociate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessAssociate,
     * or with status 400 (Bad Request) if the businessAssociate is not valid,
     * or with status 500 (Internal Server Error) if the businessAssociate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-associates")
    @Timed
    public ResponseEntity<BusinessAssociate> updateBusinessAssociate(@RequestBody BusinessAssociate businessAssociate) throws URISyntaxException {
        log.debug("REST request to update BusinessAssociate : {}", businessAssociate);
        if (businessAssociate.getId() == null) {
            return createBusinessAssociate(businessAssociate);
        }
        BusinessAssociate result = businessAssociateRepository.save(businessAssociate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessAssociate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-associates : get all the businessAssociates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businessAssociates in body
     */
    @GetMapping("/business-associates")
    @Timed
    public List<BusinessAssociate> getAllBusinessAssociates() {
        log.debug("REST request to get all BusinessAssociates");
        List<BusinessAssociate> businessAssociates = businessAssociateRepository.findAll();
        return businessAssociates;
    }

    /**
     * GET  /business-associates/:id : get the "id" businessAssociate.
     *
     * @param id the id of the businessAssociate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessAssociate, or with status 404 (Not Found)
     */
    @GetMapping("/business-associates/{id}")
    @Timed
    public ResponseEntity<BusinessAssociate> getBusinessAssociate(@PathVariable Long id) {
        log.debug("REST request to get BusinessAssociate : {}", id);
        BusinessAssociate businessAssociate = businessAssociateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(businessAssociate));
    }

    /**
     * DELETE  /business-associates/:id : delete the "id" businessAssociate.
     *
     * @param id the id of the businessAssociate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-associates/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessAssociate(@PathVariable Long id) {
        log.debug("REST request to delete BusinessAssociate : {}", id);
        businessAssociateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
