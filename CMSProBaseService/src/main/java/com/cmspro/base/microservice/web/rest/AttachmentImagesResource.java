package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.AttachmentImagesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.AttachmentImagesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.AttachmentImages}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentImagesResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentImagesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceAttachmentImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentImagesService attachmentImagesService;

    public AttachmentImagesResource(AttachmentImagesService attachmentImagesService) {
        this.attachmentImagesService = attachmentImagesService;
    }

    /**
     * {@code POST  /attachment-images} : Create a new attachmentImages.
     *
     * @param attachmentImagesDTO the attachmentImagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentImagesDTO, or with status {@code 400 (Bad Request)} if the attachmentImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-images")
    public ResponseEntity<AttachmentImagesDTO> createAttachmentImages(@Valid @RequestBody AttachmentImagesDTO attachmentImagesDTO) throws URISyntaxException {
        log.debug("REST request to save AttachmentImages : {}", attachmentImagesDTO);
        if (attachmentImagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachmentImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentImagesDTO result = attachmentImagesService.save(attachmentImagesDTO);
        return ResponseEntity.created(new URI("/api/attachment-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /attachment-images} : Updates an existing attachmentImages.
     *
     * @param attachmentImagesDTO the attachmentImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentImagesDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentImagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-images")
    public ResponseEntity<AttachmentImagesDTO> updateAttachmentImages(@Valid @RequestBody AttachmentImagesDTO attachmentImagesDTO) throws URISyntaxException {
        log.debug("REST request to update AttachmentImages : {}", attachmentImagesDTO);
        if (attachmentImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttachmentImagesDTO result = attachmentImagesService.save(attachmentImagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentImagesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /attachment-images} : get all the attachmentImages.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentImages in body.
     */
    @GetMapping("/attachment-images")
    public ResponseEntity<List<AttachmentImagesDTO>> getAllAttachmentImages(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("attachment-is-null".equals(filter)) {
            log.debug("REST request to get all AttachmentImagess where attachment is null");
            return new ResponseEntity<>(attachmentImagesService.findAllWhereAttachmentIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of AttachmentImages");
        Page<AttachmentImagesDTO> page = attachmentImagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachment-images/:id} : get the "id" attachmentImages.
     *
     * @param id the id of the attachmentImagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentImagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-images/{id}")
    public ResponseEntity<AttachmentImagesDTO> getAttachmentImages(@PathVariable String id) {
        log.debug("REST request to get AttachmentImages : {}", id);
        Optional<AttachmentImagesDTO> attachmentImagesDTO = attachmentImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentImagesDTO);
    }

    /**
     * {@code DELETE  /attachment-images/:id} : delete the "id" attachmentImages.
     *
     * @param id the id of the attachmentImagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-images/{id}")
    public ResponseEntity<Void> deleteAttachmentImages(@PathVariable String id) {
        log.debug("REST request to delete AttachmentImages : {}", id);
        attachmentImagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/attachment-images?query=:query} : search for the attachmentImages corresponding
     * to the query.
     *
     * @param query the query of the attachmentImages search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-images")
    public ResponseEntity<List<AttachmentImagesDTO>> searchAttachmentImages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentImages for query {}", query);
        Page<AttachmentImagesDTO> page = attachmentImagesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
