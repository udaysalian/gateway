package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.SchedNom;

import com.oilgascs.netra.repository.SchedNomRepository;
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
 * REST controller for managing SchedNom.
 */
@RestController
@RequestMapping("/api")
public class SchedNomResource {

    private final Logger log = LoggerFactory.getLogger(SchedNomResource.class);

    private static final String ENTITY_NAME = "schedNom";
        
    private final SchedNomRepository schedNomRepository;

    public SchedNomResource(SchedNomRepository schedNomRepository) {
        this.schedNomRepository = schedNomRepository;
    }

    /**
     * POST  /sched-noms : Create a new schedNom.
     *
     * @param schedNom the schedNom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schedNom, or with status 400 (Bad Request) if the schedNom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sched-noms")
    @Timed
    public ResponseEntity<SchedNom> createSchedNom(@RequestBody SchedNom schedNom) throws URISyntaxException {
        log.debug("REST request to save SchedNom : {}", schedNom);
        if (schedNom.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schedNom cannot already have an ID")).body(null);
        }
        SchedNom result = schedNomRepository.save(schedNom);
        return ResponseEntity.created(new URI("/api/sched-noms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sched-noms : Updates an existing schedNom.
     *
     * @param schedNom the schedNom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schedNom,
     * or with status 400 (Bad Request) if the schedNom is not valid,
     * or with status 500 (Internal Server Error) if the schedNom couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sched-noms")
    @Timed
    public ResponseEntity<SchedNom> updateSchedNom(@RequestBody SchedNom schedNom) throws URISyntaxException {
        log.debug("REST request to update SchedNom : {}", schedNom);
        if (schedNom.getId() == null) {
            return createSchedNom(schedNom);
        }
        SchedNom result = schedNomRepository.save(schedNom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schedNom.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sched-noms : get all the schedNoms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schedNoms in body
     */
    @GetMapping("/sched-noms")
    @Timed
    public List<SchedNom> getAllSchedNoms() {
        log.debug("REST request to get all SchedNoms");
        List<SchedNom> schedNoms = schedNomRepository.findAll();
        return schedNoms;
    }

    /**
     * GET  /sched-noms/:id : get the "id" schedNom.
     *
     * @param id the id of the schedNom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schedNom, or with status 404 (Not Found)
     */
    @GetMapping("/sched-noms/{id}")
    @Timed
    public ResponseEntity<SchedNom> getSchedNom(@PathVariable Long id) {
        log.debug("REST request to get SchedNom : {}", id);
        SchedNom schedNom = schedNomRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schedNom));
    }

    /**
     * DELETE  /sched-noms/:id : delete the "id" schedNom.
     *
     * @param id the id of the schedNom to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sched-noms/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchedNom(@PathVariable Long id) {
        log.debug("REST request to delete SchedNom : {}", id);
        schedNomRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
