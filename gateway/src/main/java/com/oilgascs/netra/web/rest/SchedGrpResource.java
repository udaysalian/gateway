package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.SchedGrp;

import com.oilgascs.netra.repository.SchedGrpRepository;
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
 * REST controller for managing SchedGrp.
 */
@RestController
@RequestMapping("/api")
public class SchedGrpResource {

    private final Logger log = LoggerFactory.getLogger(SchedGrpResource.class);

    private static final String ENTITY_NAME = "schedGrp";
        
    private final SchedGrpRepository schedGrpRepository;

    public SchedGrpResource(SchedGrpRepository schedGrpRepository) {
        this.schedGrpRepository = schedGrpRepository;
    }

    /**
     * POST  /sched-grps : Create a new schedGrp.
     *
     * @param schedGrp the schedGrp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schedGrp, or with status 400 (Bad Request) if the schedGrp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sched-grps")
    @Timed
    public ResponseEntity<SchedGrp> createSchedGrp(@RequestBody SchedGrp schedGrp) throws URISyntaxException {
        log.debug("REST request to save SchedGrp : {}", schedGrp);
        if (schedGrp.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schedGrp cannot already have an ID")).body(null);
        }
        SchedGrp result = schedGrpRepository.save(schedGrp);
        return ResponseEntity.created(new URI("/api/sched-grps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sched-grps : Updates an existing schedGrp.
     *
     * @param schedGrp the schedGrp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schedGrp,
     * or with status 400 (Bad Request) if the schedGrp is not valid,
     * or with status 500 (Internal Server Error) if the schedGrp couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sched-grps")
    @Timed
    public ResponseEntity<SchedGrp> updateSchedGrp(@RequestBody SchedGrp schedGrp) throws URISyntaxException {
        log.debug("REST request to update SchedGrp : {}", schedGrp);
        if (schedGrp.getId() == null) {
            return createSchedGrp(schedGrp);
        }
        SchedGrp result = schedGrpRepository.save(schedGrp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schedGrp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sched-grps : get all the schedGrps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schedGrps in body
     */
    @GetMapping("/sched-grps")
    @Timed
    public List<SchedGrp> getAllSchedGrps() {
        log.debug("REST request to get all SchedGrps");
        List<SchedGrp> schedGrps = schedGrpRepository.findAll();
        return schedGrps;
    }

    /**
     * GET  /sched-grps/:id : get the "id" schedGrp.
     *
     * @param id the id of the schedGrp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schedGrp, or with status 404 (Not Found)
     */
    @GetMapping("/sched-grps/{id}")
    @Timed
    public ResponseEntity<SchedGrp> getSchedGrp(@PathVariable Long id) {
        log.debug("REST request to get SchedGrp : {}", id);
        SchedGrp schedGrp = schedGrpRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schedGrp));
    }

    /**
     * DELETE  /sched-grps/:id : delete the "id" schedGrp.
     *
     * @param id the id of the schedGrp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sched-grps/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchedGrp(@PathVariable Long id) {
        log.debug("REST request to delete SchedGrp : {}", id);
        schedGrpRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
