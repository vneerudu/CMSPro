package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.LogosService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.LogosDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.cmspro.base.microservice.domain.Logos}.
 */
@RestController
@RequestMapping("/api")
public class LogosResource {

    private final Logger log = LoggerFactory.getLogger(LogosResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceLogos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogosService logosService;

    public LogosResource(LogosService logosService) {
        this.logosService = logosService;
    }

    /**
     * {@code POST  /logos} : Create a new logos.
     *
     * @param logosDTO the logosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logosDTO, or with status {@code 400 (Bad Request)} if the logos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/logos")
    public ResponseEntity<LogosDTO> createLogos(@Valid @RequestBody LogosDTO logosDTO) throws URISyntaxException {
        log.debug("REST request to save Logos : {}", logosDTO);
        if (logosDTO.getId() != null) {
            throw new BadRequestAlertException("A new logos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogosDTO result = logosService.save(logosDTO);
        return ResponseEntity.created(new URI("/api/logos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /logos} : Updates an existing logos.
     *
     * @param logosDTO the logosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logosDTO,
     * or with status {@code 400 (Bad Request)} if the logosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/logos")
    public ResponseEntity<LogosDTO> updateLogos(@Valid @RequestBody LogosDTO logosDTO) throws URISyntaxException {
        log.debug("REST request to update Logos : {}", logosDTO);
        if (logosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogosDTO result = logosService.save(logosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, logosDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /logos} : get all the logos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logos in body.
     */
    @GetMapping("/logos")
    public ResponseEntity<List<LogosDTO>> getAllLogos(Pageable pageable) {
        log.debug("REST request to get a page of Logos");
        Page<LogosDTO> page = logosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /logos/:id} : get the "id" logos.
     *
     * @param id the id of the logosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/logos/{id}")
    public ResponseEntity<LogosDTO> getLogos(@PathVariable String id) {
        log.debug("REST request to get Logos : {}", id);
        Optional<LogosDTO> logosDTO = logosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logosDTO);
    }

    /**
     * {@code DELETE  /logos/:id} : delete the "id" logos.
     *
     * @param id the id of the logosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/logos/{id}")
    public ResponseEntity<Void> deleteLogos(@PathVariable String id) {
        log.debug("REST request to delete Logos : {}", id);
        logosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/logos?query=:query} : search for the logos corresponding
     * to the query.
     *
     * @param query the query of the logos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/logos")
    public ResponseEntity<List<LogosDTO>> searchLogos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Logos for query {}", query);
        Page<LogosDTO> page = logosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
