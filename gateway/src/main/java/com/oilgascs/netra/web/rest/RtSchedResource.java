package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.RtSched;

import com.oilgascs.netra.repository.RtSchedRepository;
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
 * REST controller for managing RtSched.
 */
@RestController
@RequestMapping("/api")
public class RtSchedResource {

    private final Logger log = LoggerFactory.getLogger(RtSchedResource.class);

    private static final String ENTITY_NAME = "rtSched";
        
    private final RtSchedRepository rtSchedRepository;

    public RtSchedResource(RtSchedRepository rtSchedRepository) {
        this.rtSchedRepository = rtSchedRepository;
    }

    /**
     * POST  /rt-scheds : Create a new rtSched.
     *
     * @param rtSched the rtSched to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rtSched, or with status 400 (Bad Request) if the rtSched has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rt-scheds")
    @Timed
    public ResponseEntity<RtSched> createRtSched(@RequestBody RtSched rtSched) throws URISyntaxException {
        log.debug("REST request to save RtSched : {}", rtSched);
        if (rtSched.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rtSched cannot already have an ID")).body(null);
        }
        RtSched result = rtSchedRepository.save(rtSched);
        return ResponseEntity.created(new URI("/api/rt-scheds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rt-scheds : Updates an existing rtSched.
     *
     * @param rtSched the rtSched to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rtSched,
     * or with status 400 (Bad Request) if the rtSched is not valid,
     * or with status 500 (Internal Server Error) if the rtSched couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rt-scheds")
    @Timed
    public ResponseEntity<RtSched> updateRtSched(@RequestBody RtSched rtSched) throws URISyntaxException {
        log.debug("REST request to update RtSched : {}", rtSched);
        if (rtSched.getId() == null) {
            return createRtSched(rtSched);
        }
        RtSched result = rtSchedRepository.save(rtSched);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rtSched.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rt-scheds : get all the rtScheds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rtScheds in body
     */
    @GetMapping("/rt-scheds")
    @Timed
    public List<RtSched> getAllRtScheds() {
        log.debug("REST request to get all RtScheds");
        List<RtSched> rtScheds = rtSchedRepository.findAll();
        return rtScheds;
    }

    /**
     * GET  /rt-scheds/:id : get the "id" rtSched.
     *
     * @param id the id of the rtSched to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rtSched, or with status 404 (Not Found)
     */
    @GetMapping("/rt-scheds/{id}")
    @Timed
    public ResponseEntity<RtSched> getRtSched(@PathVariable Long id) {
        log.debug("REST request to get RtSched : {}", id);
        RtSched rtSched = rtSchedRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rtSched));
    }

    /**
     * DELETE  /rt-scheds/:id : delete the "id" rtSched.
     *
     * @param id the id of the rtSched to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rt-scheds/{id}")
    @Timed
    public ResponseEntity<Void> deleteRtSched(@PathVariable Long id) {
        log.debug("REST request to delete RtSched : {}", id);
        rtSchedRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
