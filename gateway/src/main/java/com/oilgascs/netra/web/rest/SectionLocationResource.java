package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.SectionLocation;

import com.oilgascs.netra.repository.SectionLocationRepository;
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
 * REST controller for managing SectionLocation.
 */
@RestController
@RequestMapping("/api")
public class SectionLocationResource {

    private final Logger log = LoggerFactory.getLogger(SectionLocationResource.class);

    private static final String ENTITY_NAME = "sectionLocation";
        
    private final SectionLocationRepository sectionLocationRepository;

    public SectionLocationResource(SectionLocationRepository sectionLocationRepository) {
        this.sectionLocationRepository = sectionLocationRepository;
    }

    /**
     * POST  /section-locations : Create a new sectionLocation.
     *
     * @param sectionLocation the sectionLocation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sectionLocation, or with status 400 (Bad Request) if the sectionLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/section-locations")
    @Timed
    public ResponseEntity<SectionLocation> createSectionLocation(@RequestBody SectionLocation sectionLocation) throws URISyntaxException {
        log.debug("REST request to save SectionLocation : {}", sectionLocation);
        if (sectionLocation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sectionLocation cannot already have an ID")).body(null);
        }
        SectionLocation result = sectionLocationRepository.save(sectionLocation);
        return ResponseEntity.created(new URI("/api/section-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /section-locations : Updates an existing sectionLocation.
     *
     * @param sectionLocation the sectionLocation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sectionLocation,
     * or with status 400 (Bad Request) if the sectionLocation is not valid,
     * or with status 500 (Internal Server Error) if the sectionLocation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/section-locations")
    @Timed
    public ResponseEntity<SectionLocation> updateSectionLocation(@RequestBody SectionLocation sectionLocation) throws URISyntaxException {
        log.debug("REST request to update SectionLocation : {}", sectionLocation);
        if (sectionLocation.getId() == null) {
            return createSectionLocation(sectionLocation);
        }
        SectionLocation result = sectionLocationRepository.save(sectionLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sectionLocation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /section-locations : get all the sectionLocations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sectionLocations in body
     */
    @GetMapping("/section-locations")
    @Timed
    public List<SectionLocation> getAllSectionLocations() {
        log.debug("REST request to get all SectionLocations");
        List<SectionLocation> sectionLocations = sectionLocationRepository.findAll();
        return sectionLocations;
    }

    /**
     * GET  /section-locations/:id : get the "id" sectionLocation.
     *
     * @param id the id of the sectionLocation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectionLocation, or with status 404 (Not Found)
     */
    @GetMapping("/section-locations/{id}")
    @Timed
    public ResponseEntity<SectionLocation> getSectionLocation(@PathVariable Long id) {
        log.debug("REST request to get SectionLocation : {}", id);
        SectionLocation sectionLocation = sectionLocationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sectionLocation));
    }

    /**
     * DELETE  /section-locations/:id : delete the "id" sectionLocation.
     *
     * @param id the id of the sectionLocation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/section-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSectionLocation(@PathVariable Long id) {
        log.debug("REST request to delete SectionLocation : {}", id);
        sectionLocationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
