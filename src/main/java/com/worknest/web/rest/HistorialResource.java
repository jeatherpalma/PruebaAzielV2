package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.Historial;

import com.worknest.repository.HistorialRepository;
import com.worknest.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Historial.
 */
@RestController
@RequestMapping("/api")
public class HistorialResource {

    private final Logger log = LoggerFactory.getLogger(HistorialResource.class);

    private static final String ENTITY_NAME = "historial";

    private final HistorialRepository historialRepository;

    public HistorialResource(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    /**
     * POST  /historials : Create a new historial.
     *
     * @param historial the historial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historial, or with status 400 (Bad Request) if the historial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historials")
    @Timed
    public ResponseEntity<Historial> createHistorial(@Valid @RequestBody Historial historial) throws URISyntaxException {
        log.debug("REST request to save Historial : {}", historial);
        if (historial.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new historial cannot already have an ID")).body(null);
        }
        Historial result = historialRepository.save(historial);
        return ResponseEntity.created(new URI("/api/historials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historials : Updates an existing historial.
     *
     * @param historial the historial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historial,
     * or with status 400 (Bad Request) if the historial is not valid,
     * or with status 500 (Internal Server Error) if the historial couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/historials")
    @Timed
    public ResponseEntity<Historial> updateHistorial(@Valid @RequestBody Historial historial) throws URISyntaxException {
        log.debug("REST request to update Historial : {}", historial);
        if (historial.getId() == null) {
            return createHistorial(historial);
        }
        Historial result = historialRepository.save(historial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historials : get all the historials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of historials in body
     */
    @GetMapping("/historials")
    @Timed
    public List<Historial> getAllHistorials() {
        log.debug("REST request to get all Historials");
        return historialRepository.findAll();
        }

    /**
     * GET  /historials/:id : get the "id" historial.
     *
     * @param id the id of the historial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historial, or with status 404 (Not Found)
     */
    @GetMapping("/historials/{id}")
    @Timed
    public ResponseEntity<Historial> getHistorial(@PathVariable Long id) {
        log.debug("REST request to get Historial : {}", id);
        Historial historial = historialRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(historial));
    }

    /**
     * DELETE  /historials/:id : delete the "id" historial.
     *
     * @param id the id of the historial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/historials/{id}")
    @Timed
    public ResponseEntity<Void> deleteHistorial(@PathVariable Long id) {
        log.debug("REST request to delete Historial : {}", id);
        historialRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
