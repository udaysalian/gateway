package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.RtSchedVald;

import com.oilgascs.netra.repository.RtSchedValdRepository;
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
 * REST controller for managing RtSchedVald.
 */
@RestController
@RequestMapping("/api")
public class RtSchedValdResource {

    private final Logger log = LoggerFactory.getLogger(RtSchedValdResource.class);

    private static final String ENTITY_NAME = "rtSchedVald";
        
    private final RtSchedValdRepository rtSchedValdRepository;

    public RtSchedValdResource(RtSchedValdRepository rtSchedValdRepository) {
        this.rtSchedValdRepository = rtSchedValdRepository;
    }

    /**
     * POST  /rt-sched-valds : Create a new rtSchedVald.
     *
     * @param rtSchedVald the rtSchedVald to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rtSchedVald, or with status 400 (Bad Request) if the rtSchedVald has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rt-sched-valds")
    @Timed
    public ResponseEntity<RtSchedVald> createRtSchedVald(@RequestBody RtSchedVald rtSchedVald) throws URISyntaxException {
        log.debug("REST request to save RtSchedVald : {}", rtSchedVald);
        if (rtSchedVald.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rtSchedVald cannot already have an ID")).body(null);
        }
        RtSchedVald result = rtSchedValdRepository.save(rtSchedVald);
        return ResponseEntity.created(new URI("/api/rt-sched-valds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rt-sched-valds : Updates an existing rtSchedVald.
     *
     * @param rtSchedVald the rtSchedVald to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rtSchedVald,
     * or with status 400 (Bad Request) if the rtSchedVald is not valid,
     * or with status 500 (Internal Server Error) if the rtSchedVald couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rt-sched-valds")
    @Timed
    public ResponseEntity<RtSchedVald> updateRtSchedVald(@RequestBody RtSchedVald rtSchedVald) throws URISyntaxException {
        log.debug("REST request to update RtSchedVald : {}", rtSchedVald);
        if (rtSchedVald.getId() == null) {
            return createRtSchedVald(rtSchedVald);
        }
        RtSchedVald result = rtSchedValdRepository.save(rtSchedVald);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rtSchedVald.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rt-sched-valds : get all the rtSchedValds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rtSchedValds in body
     */
    @GetMapping("/rt-sched-valds")
    @Timed
    public List<RtSchedVald> getAllRtSchedValds() {
        log.debug("REST request to get all RtSchedValds");
        List<RtSchedVald> rtSchedValds = rtSchedValdRepository.findAll();
        return rtSchedValds;
    }

    /**
     * GET  /rt-sched-valds/:id : get the "id" rtSchedVald.
     *
     * @param id the id of the rtSchedVald to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rtSchedVald, or with status 404 (Not Found)
     */
    @GetMapping("/rt-sched-valds/{id}")
    @Timed
    public ResponseEntity<RtSchedVald> getRtSchedVald(@PathVariable Long id) {
        log.debug("REST request to get RtSchedVald : {}", id);
        RtSchedVald rtSchedVald = rtSchedValdRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rtSchedVald));
    }

    /**
     * DELETE  /rt-sched-valds/:id : delete the "id" rtSchedVald.
     *
     * @param id the id of the rtSchedVald to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rt-sched-valds/{id}")
    @Timed
    public ResponseEntity<Void> deleteRtSchedVald(@PathVariable Long id) {
        log.debug("REST request to delete RtSchedVald : {}", id);
        rtSchedValdRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
