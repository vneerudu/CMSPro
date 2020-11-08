package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.AttachmentOthersService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.AttachmentOthersDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.AttachmentOthers}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentOthersResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentOthersResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceAttachmentOthers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentOthersService attachmentOthersService;

    public AttachmentOthersResource(AttachmentOthersService attachmentOthersService) {
        this.attachmentOthersService = attachmentOthersService;
    }

    /**
     * {@code POST  /attachment-others} : Create a new attachmentOthers.
     *
     * @param attachmentOthersDTO the attachmentOthersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentOthersDTO, or with status {@code 400 (Bad Request)} if the attachmentOthers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-others")
    public ResponseEntity<AttachmentOthersDTO> createAttachmentOthers(@Valid @RequestBody AttachmentOthersDTO attachmentOthersDTO) throws URISyntaxException {
        log.debug("REST request to save AttachmentOthers : {}", attachmentOthersDTO);
        if (attachmentOthersDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachmentOthers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentOthersDTO result = attachmentOthersService.save(attachmentOthersDTO);
        return ResponseEntity.created(new URI("/api/attachment-others/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /attachment-others} : Updates an existing attachmentOthers.
     *
     * @param attachmentOthersDTO the attachmentOthersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentOthersDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentOthersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentOthersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-others")
    public ResponseEntity<AttachmentOthersDTO> updateAttachmentOthers(@Valid @RequestBody AttachmentOthersDTO attachmentOthersDTO) throws URISyntaxException {
        log.debug("REST request to update AttachmentOthers : {}", attachmentOthersDTO);
        if (attachmentOthersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttachmentOthersDTO result = attachmentOthersService.save(attachmentOthersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentOthersDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /attachment-others} : get all the attachmentOthers.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentOthers in body.
     */
    @GetMapping("/attachment-others")
    public ResponseEntity<List<AttachmentOthersDTO>> getAllAttachmentOthers(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("attachment-is-null".equals(filter)) {
            log.debug("REST request to get all AttachmentOtherss where attachment is null");
            return new ResponseEntity<>(attachmentOthersService.findAllWhereAttachmentIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of AttachmentOthers");
        Page<AttachmentOthersDTO> page = attachmentOthersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachment-others/:id} : get the "id" attachmentOthers.
     *
     * @param id the id of the attachmentOthersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentOthersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-others/{id}")
    public ResponseEntity<AttachmentOthersDTO> getAttachmentOthers(@PathVariable String id) {
        log.debug("REST request to get AttachmentOthers : {}", id);
        Optional<AttachmentOthersDTO> attachmentOthersDTO = attachmentOthersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentOthersDTO);
    }

    /**
     * {@code DELETE  /attachment-others/:id} : delete the "id" attachmentOthers.
     *
     * @param id the id of the attachmentOthersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-others/{id}")
    public ResponseEntity<Void> deleteAttachmentOthers(@PathVariable String id) {
        log.debug("REST request to delete AttachmentOthers : {}", id);
        attachmentOthersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/attachment-others?query=:query} : search for the attachmentOthers corresponding
     * to the query.
     *
     * @param query the query of the attachmentOthers search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-others")
    public ResponseEntity<List<AttachmentOthersDTO>> searchAttachmentOthers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentOthers for query {}", query);
        Page<AttachmentOthersDTO> page = attachmentOthersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
