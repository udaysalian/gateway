package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.SchedEvent;

import com.oilgascs.netra.repository.SchedEventRepository;
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
 * REST controller for managing SchedEvent.
 */
@RestController
@RequestMapping("/api")
public class SchedEventResource {

    private final Logger log = LoggerFactory.getLogger(SchedEventResource.class);

    private static final String ENTITY_NAME = "schedEvent";
        
    private final SchedEventRepository schedEventRepository;

    public SchedEventResource(SchedEventRepository schedEventRepository) {
        this.schedEventRepository = schedEventRepository;
    }

    /**
     * POST  /sched-events : Create a new schedEvent.
     *
     * @param schedEvent the schedEvent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schedEvent, or with status 400 (Bad Request) if the schedEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sched-events")
    @Timed
    public ResponseEntity<SchedEvent> createSchedEvent(@RequestBody SchedEvent schedEvent) throws URISyntaxException {
        log.debug("REST request to save SchedEvent : {}", schedEvent);
        if (schedEvent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schedEvent cannot already have an ID")).body(null);
        }
        SchedEvent result = schedEventRepository.save(schedEvent);
        return ResponseEntity.created(new URI("/api/sched-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sched-events : Updates an existing schedEvent.
     *
     * @param schedEvent the schedEvent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schedEvent,
     * or with status 400 (Bad Request) if the schedEvent is not valid,
     * or with status 500 (Internal Server Error) if the schedEvent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sched-events")
    @Timed
    public ResponseEntity<SchedEvent> updateSchedEvent(@RequestBody SchedEvent schedEvent) throws URISyntaxException {
        log.debug("REST request to update SchedEvent : {}", schedEvent);
        if (schedEvent.getId() == null) {
            return createSchedEvent(schedEvent);
        }
        SchedEvent result = schedEventRepository.save(schedEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schedEvent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sched-events : get all the schedEvents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schedEvents in body
     */
    @GetMapping("/sched-events")
    @Timed
    public List<SchedEvent> getAllSchedEvents() {
        log.debug("REST request to get all SchedEvents");
        List<SchedEvent> schedEvents = schedEventRepository.findAll();
        return schedEvents;
    }

    /**
     * GET  /sched-events/:id : get the "id" schedEvent.
     *
     * @param id the id of the schedEvent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schedEvent, or with status 404 (Not Found)
     */
    @GetMapping("/sched-events/{id}")
    @Timed
    public ResponseEntity<SchedEvent> getSchedEvent(@PathVariable Long id) {
        log.debug("REST request to get SchedEvent : {}", id);
        SchedEvent schedEvent = schedEventRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schedEvent));
    }

    /**
     * DELETE  /sched-events/:id : delete the "id" schedEvent.
     *
     * @param id the id of the schedEvent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sched-events/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchedEvent(@PathVariable Long id) {
        log.debug("REST request to delete SchedEvent : {}", id);
        schedEventRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
