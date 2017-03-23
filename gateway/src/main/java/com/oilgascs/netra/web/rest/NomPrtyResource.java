package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.NomPrty;

import com.oilgascs.netra.repository.NomPrtyRepository;
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
 * REST controller for managing NomPrty.
 */
@RestController
@RequestMapping("/api")
public class NomPrtyResource {

    private final Logger log = LoggerFactory.getLogger(NomPrtyResource.class);

    private static final String ENTITY_NAME = "nomPrty";
        
    private final NomPrtyRepository nomPrtyRepository;

    public NomPrtyResource(NomPrtyRepository nomPrtyRepository) {
        this.nomPrtyRepository = nomPrtyRepository;
    }

    /**
     * POST  /nom-prties : Create a new nomPrty.
     *
     * @param nomPrty the nomPrty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nomPrty, or with status 400 (Bad Request) if the nomPrty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nom-prties")
    @Timed
    public ResponseEntity<NomPrty> createNomPrty(@RequestBody NomPrty nomPrty) throws URISyntaxException {
        log.debug("REST request to save NomPrty : {}", nomPrty);
        if (nomPrty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new nomPrty cannot already have an ID")).body(null);
        }
        NomPrty result = nomPrtyRepository.save(nomPrty);
        return ResponseEntity.created(new URI("/api/nom-prties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nom-prties : Updates an existing nomPrty.
     *
     * @param nomPrty the nomPrty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nomPrty,
     * or with status 400 (Bad Request) if the nomPrty is not valid,
     * or with status 500 (Internal Server Error) if the nomPrty couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nom-prties")
    @Timed
    public ResponseEntity<NomPrty> updateNomPrty(@RequestBody NomPrty nomPrty) throws URISyntaxException {
        log.debug("REST request to update NomPrty : {}", nomPrty);
        if (nomPrty.getId() == null) {
            return createNomPrty(nomPrty);
        }
        NomPrty result = nomPrtyRepository.save(nomPrty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nomPrty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nom-prties : get all the nomPrties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nomPrties in body
     */
    @GetMapping("/nom-prties")
    @Timed
    public List<NomPrty> getAllNomPrties() {
        log.debug("REST request to get all NomPrties");
        List<NomPrty> nomPrties = nomPrtyRepository.findAll();
        return nomPrties;
    }

    /**
     * GET  /nom-prties/:id : get the "id" nomPrty.
     *
     * @param id the id of the nomPrty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nomPrty, or with status 404 (Not Found)
     */
    @GetMapping("/nom-prties/{id}")
    @Timed
    public ResponseEntity<NomPrty> getNomPrty(@PathVariable Long id) {
        log.debug("REST request to get NomPrty : {}", id);
        NomPrty nomPrty = nomPrtyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nomPrty));
    }

    /**
     * DELETE  /nom-prties/:id : delete the "id" nomPrty.
     *
     * @param id the id of the nomPrty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nom-prties/{id}")
    @Timed
    public ResponseEntity<Void> deleteNomPrty(@PathVariable Long id) {
        log.debug("REST request to delete NomPrty : {}", id);
        nomPrtyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
