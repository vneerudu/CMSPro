package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.LanguagesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.LanguagesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.Languages}.
 */
@RestController
@RequestMapping("/api")
public class LanguagesResource {

    private final Logger log = LoggerFactory.getLogger(LanguagesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceLanguages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LanguagesService languagesService;

    public LanguagesResource(LanguagesService languagesService) {
        this.languagesService = languagesService;
    }

    /**
     * {@code POST  /languages} : Create a new languages.
     *
     * @param languagesDTO the languagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new languagesDTO, or with status {@code 400 (Bad Request)} if the languages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/languages")
    public ResponseEntity<LanguagesDTO> createLanguages(@Valid @RequestBody LanguagesDTO languagesDTO) throws URISyntaxException {
        log.debug("REST request to save Languages : {}", languagesDTO);
        if (languagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new languages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LanguagesDTO result = languagesService.save(languagesDTO);
        return ResponseEntity.created(new URI("/api/languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /languages} : Updates an existing languages.
     *
     * @param languagesDTO the languagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated languagesDTO,
     * or with status {@code 400 (Bad Request)} if the languagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the languagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/languages")
    public ResponseEntity<LanguagesDTO> updateLanguages(@Valid @RequestBody LanguagesDTO languagesDTO) throws URISyntaxException {
        log.debug("REST request to update Languages : {}", languagesDTO);
        if (languagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LanguagesDTO result = languagesService.save(languagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, languagesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /languages} : get all the languages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of languages in body.
     */
    @GetMapping("/languages")
    public ResponseEntity<List<LanguagesDTO>> getAllLanguages(Pageable pageable) {
        log.debug("REST request to get a page of Languages");
        Page<LanguagesDTO> page = languagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /languages/:id} : get the "id" languages.
     *
     * @param id the id of the languagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the languagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/languages/{id}")
    public ResponseEntity<LanguagesDTO> getLanguages(@PathVariable String id) {
        log.debug("REST request to get Languages : {}", id);
        Optional<LanguagesDTO> languagesDTO = languagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(languagesDTO);
    }

    /**
     * {@code DELETE  /languages/:id} : delete the "id" languages.
     *
     * @param id the id of the languagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/languages/{id}")
    public ResponseEntity<Void> deleteLanguages(@PathVariable String id) {
        log.debug("REST request to delete Languages : {}", id);
        languagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/languages?query=:query} : search for the languages corresponding
     * to the query.
     *
     * @param query the query of the languages search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/languages")
    public ResponseEntity<List<LanguagesDTO>> searchLanguages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Languages for query {}", query);
        Page<LanguagesDTO> page = languagesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
