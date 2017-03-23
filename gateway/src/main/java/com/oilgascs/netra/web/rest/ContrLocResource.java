package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.ContrLoc;

import com.oilgascs.netra.repository.ContrLocRepository;
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
 * REST controller for managing ContrLoc.
 */
@RestController
@RequestMapping("/api")
public class ContrLocResource {

    private final Logger log = LoggerFactory.getLogger(ContrLocResource.class);

    private static final String ENTITY_NAME = "contrLoc";
        
    private final ContrLocRepository contrLocRepository;

    public ContrLocResource(ContrLocRepository contrLocRepository) {
        this.contrLocRepository = contrLocRepository;
    }

    /**
     * POST  /contr-locs : Create a new contrLoc.
     *
     * @param contrLoc the contrLoc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contrLoc, or with status 400 (Bad Request) if the contrLoc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contr-locs")
    @Timed
    public ResponseEntity<ContrLoc> createContrLoc(@RequestBody ContrLoc contrLoc) throws URISyntaxException {
        log.debug("REST request to save ContrLoc : {}", contrLoc);
        if (contrLoc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contrLoc cannot already have an ID")).body(null);
        }
        ContrLoc result = contrLocRepository.save(contrLoc);
        return ResponseEntity.created(new URI("/api/contr-locs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contr-locs : Updates an existing contrLoc.
     *
     * @param contrLoc the contrLoc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contrLoc,
     * or with status 400 (Bad Request) if the contrLoc is not valid,
     * or with status 500 (Internal Server Error) if the contrLoc couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contr-locs")
    @Timed
    public ResponseEntity<ContrLoc> updateContrLoc(@RequestBody ContrLoc contrLoc) throws URISyntaxException {
        log.debug("REST request to update ContrLoc : {}", contrLoc);
        if (contrLoc.getId() == null) {
            return createContrLoc(contrLoc);
        }
        ContrLoc result = contrLocRepository.save(contrLoc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contrLoc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contr-locs : get all the contrLocs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contrLocs in body
     */
    @GetMapping("/contr-locs")
    @Timed
    public List<ContrLoc> getAllContrLocs() {
        log.debug("REST request to get all ContrLocs");
        List<ContrLoc> contrLocs = contrLocRepository.findAll();
        return contrLocs;
    }

    /**
     * GET  /contr-locs/:id : get the "id" contrLoc.
     *
     * @param id the id of the contrLoc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contrLoc, or with status 404 (Not Found)
     */
    @GetMapping("/contr-locs/{id}")
    @Timed
    public ResponseEntity<ContrLoc> getContrLoc(@PathVariable Long id) {
        log.debug("REST request to get ContrLoc : {}", id);
        ContrLoc contrLoc = contrLocRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contrLoc));
    }

    /**
     * DELETE  /contr-locs/:id : delete the "id" contrLoc.
     *
     * @param id the id of the contrLoc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contr-locs/{id}")
    @Timed
    public ResponseEntity<Void> deleteContrLoc(@PathVariable Long id) {
        log.debug("REST request to delete ContrLoc : {}", id);
        contrLocRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
