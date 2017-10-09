package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.Ventas;

import com.worknest.repository.VentasRepository;
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
 * REST controller for managing Ventas.
 */
@RestController
@RequestMapping("/api")
public class VentasResource {

    private final Logger log = LoggerFactory.getLogger(VentasResource.class);

    private static final String ENTITY_NAME = "ventas";

    private final VentasRepository ventasRepository;

    public VentasResource(VentasRepository ventasRepository) {
        this.ventasRepository = ventasRepository;
    }

    /**
     * POST  /ventas : Create a new ventas.
     *
     * @param ventas the ventas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ventas, or with status 400 (Bad Request) if the ventas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ventas")
    @Timed
    public ResponseEntity<Ventas> createVentas(@Valid @RequestBody Ventas ventas) throws URISyntaxException {
        log.debug("REST request to save Ventas : {}", ventas);
        if (ventas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ventas cannot already have an ID")).body(null);
        }
        Ventas result = ventasRepository.save(ventas);
        return ResponseEntity.created(new URI("/api/ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ventas : Updates an existing ventas.
     *
     * @param ventas the ventas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ventas,
     * or with status 400 (Bad Request) if the ventas is not valid,
     * or with status 500 (Internal Server Error) if the ventas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ventas")
    @Timed
    public ResponseEntity<Ventas> updateVentas(@Valid @RequestBody Ventas ventas) throws URISyntaxException {
        log.debug("REST request to update Ventas : {}", ventas);
        if (ventas.getId() == null) {
            return createVentas(ventas);
        }
        Ventas result = ventasRepository.save(ventas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ventas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ventas : get all the ventas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ventas in body
     */
    @GetMapping("/ventas")
    @Timed
    public List<Ventas> getAllVentas() {
        log.debug("REST request to get all Ventas");
        return ventasRepository.findAll();
        }

    /**
     * GET  /ventas/:id : get the "id" ventas.
     *
     * @param id the id of the ventas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ventas, or with status 404 (Not Found)
     */
    @GetMapping("/ventas/{id}")
    @Timed
    public ResponseEntity<Ventas> getVentas(@PathVariable Long id) {
        log.debug("REST request to get Ventas : {}", id);
        Ventas ventas = ventasRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ventas));
    }

    /**
     * DELETE  /ventas/:id : delete the "id" ventas.
     *
     * @param id the id of the ventas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ventas/{id}")
    @Timed
    public ResponseEntity<Void> deleteVentas(@PathVariable Long id) {
        log.debug("REST request to delete Ventas : {}", id);
        ventasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
