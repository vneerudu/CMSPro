package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.AttachmentsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.AttachmentsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.Attachments}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentsResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceAttachments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentsService attachmentsService;

    public AttachmentsResource(AttachmentsService attachmentsService) {
        this.attachmentsService = attachmentsService;
    }

    /**
     * {@code POST  /attachments} : Create a new attachments.
     *
     * @param attachmentsDTO the attachmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentsDTO, or with status {@code 400 (Bad Request)} if the attachments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachments")
    public ResponseEntity<AttachmentsDTO> createAttachments(@Valid @RequestBody AttachmentsDTO attachmentsDTO) throws URISyntaxException {
        log.debug("REST request to save Attachments : {}", attachmentsDTO);
        if (attachmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentsDTO result = attachmentsService.save(attachmentsDTO);
        return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /attachments} : Updates an existing attachments.
     *
     * @param attachmentsDTO the attachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachments")
    public ResponseEntity<AttachmentsDTO> updateAttachments(@Valid @RequestBody AttachmentsDTO attachmentsDTO) throws URISyntaxException {
        log.debug("REST request to update Attachments : {}", attachmentsDTO);
        if (attachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttachmentsDTO result = attachmentsService.save(attachmentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /attachments} : get all the attachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachments in body.
     */
    @GetMapping("/attachments")
    public ResponseEntity<List<AttachmentsDTO>> getAllAttachments(Pageable pageable) {
        log.debug("REST request to get a page of Attachments");
        Page<AttachmentsDTO> page = attachmentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachments/:id} : get the "id" attachments.
     *
     * @param id the id of the attachmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachments/{id}")
    public ResponseEntity<AttachmentsDTO> getAttachments(@PathVariable String id) {
        log.debug("REST request to get Attachments : {}", id);
        Optional<AttachmentsDTO> attachmentsDTO = attachmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentsDTO);
    }

    /**
     * {@code DELETE  /attachments/:id} : delete the "id" attachments.
     *
     * @param id the id of the attachmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachments/{id}")
    public ResponseEntity<Void> deleteAttachments(@PathVariable String id) {
        log.debug("REST request to delete Attachments : {}", id);
        attachmentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/attachments?query=:query} : search for the attachments corresponding
     * to the query.
     *
     * @param query the query of the attachments search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachments")
    public ResponseEntity<List<AttachmentsDTO>> searchAttachments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Attachments for query {}", query);
        Page<AttachmentsDTO> page = attachmentsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
