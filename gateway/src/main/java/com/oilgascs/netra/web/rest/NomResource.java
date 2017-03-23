package com.oilgascs.netra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oilgascs.netra.domain.Nom;

import com.oilgascs.netra.repository.NomRepository;
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
 * REST controller for managing Nom.
 */
@RestController
@RequestMapping("/api")
public class NomResource {

    private final Logger log = LoggerFactory.getLogger(NomResource.class);

    private static final String ENTITY_NAME = "nom";
        
    private final NomRepository nomRepository;

    public NomResource(NomRepository nomRepository) {
        this.nomRepository = nomRepository;
    }

    /**
     * POST  /noms : Create a new nom.
     *
     * @param nom the nom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nom, or with status 400 (Bad Request) if the nom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/noms")
    @Timed
    public ResponseEntity<Nom> createNom(@RequestBody Nom nom) throws URISyntaxException {
        log.debug("REST request to save Nom : {}", nom);
        if (nom.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new nom cannot already have an ID")).body(null);
        }
        Nom result = nomRepository.save(nom);
        return ResponseEntity.created(new URI("/api/noms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /noms : Updates an existing nom.
     *
     * @param nom the nom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nom,
     * or with status 400 (Bad Request) if the nom is not valid,
     * or with status 500 (Internal Server Error) if the nom couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/noms")
    @Timed
    public ResponseEntity<Nom> updateNom(@RequestBody Nom nom) throws URISyntaxException {
        log.debug("REST request to update Nom : {}", nom);
        if (nom.getId() == null) {
            return createNom(nom);
        }
        Nom result = nomRepository.save(nom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nom.getId().toString()))
            .body(result);
    }

    /**
     * GET  /noms : get all the noms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of noms in body
     */
    @GetMapping("/noms")
    @Timed
    public List<Nom> getAllNoms() {
        log.debug("REST request to get all Noms");
        List<Nom> noms = nomRepository.findAll();
        return noms;
    }

    /**
     * GET  /noms/:id : get the "id" nom.
     *
     * @param id the id of the nom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nom, or with status 404 (Not Found)
     */
    @GetMapping("/noms/{id}")
    @Timed
    public ResponseEntity<Nom> getNom(@PathVariable Long id) {
        log.debug("REST request to get Nom : {}", id);
        Nom nom = nomRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nom));
    }

    /**
     * DELETE  /noms/:id : delete the "id" nom.
     *
     * @param id the id of the nom to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/noms/{id}")
    @Timed
    public ResponseEntity<Void> deleteNom(@PathVariable Long id) {
        log.debug("REST request to delete Nom : {}", id);
        nomRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
