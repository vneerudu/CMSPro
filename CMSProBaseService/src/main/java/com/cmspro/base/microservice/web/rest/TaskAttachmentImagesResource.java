package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.TaskAttachmentImagesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.TaskAttachmentImagesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.TaskAttachmentImages}.
 */
@RestController
@RequestMapping("/api")
public class TaskAttachmentImagesResource {

    private final Logger log = LoggerFactory.getLogger(TaskAttachmentImagesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceTaskAttachmentImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskAttachmentImagesService taskAttachmentImagesService;

    public TaskAttachmentImagesResource(TaskAttachmentImagesService taskAttachmentImagesService) {
        this.taskAttachmentImagesService = taskAttachmentImagesService;
    }

    /**
     * {@code POST  /task-attachment-images} : Create a new taskAttachmentImages.
     *
     * @param taskAttachmentImagesDTO the taskAttachmentImagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskAttachmentImagesDTO, or with status {@code 400 (Bad Request)} if the taskAttachmentImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-attachment-images")
    public ResponseEntity<TaskAttachmentImagesDTO> createTaskAttachmentImages(@Valid @RequestBody TaskAttachmentImagesDTO taskAttachmentImagesDTO) throws URISyntaxException {
        log.debug("REST request to save TaskAttachmentImages : {}", taskAttachmentImagesDTO);
        if (taskAttachmentImagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskAttachmentImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskAttachmentImagesDTO result = taskAttachmentImagesService.save(taskAttachmentImagesDTO);
        return ResponseEntity.created(new URI("/api/task-attachment-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /task-attachment-images} : Updates an existing taskAttachmentImages.
     *
     * @param taskAttachmentImagesDTO the taskAttachmentImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskAttachmentImagesDTO,
     * or with status {@code 400 (Bad Request)} if the taskAttachmentImagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskAttachmentImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-attachment-images")
    public ResponseEntity<TaskAttachmentImagesDTO> updateTaskAttachmentImages(@Valid @RequestBody TaskAttachmentImagesDTO taskAttachmentImagesDTO) throws URISyntaxException {
        log.debug("REST request to update TaskAttachmentImages : {}", taskAttachmentImagesDTO);
        if (taskAttachmentImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskAttachmentImagesDTO result = taskAttachmentImagesService.save(taskAttachmentImagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskAttachmentImagesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /task-attachment-images} : get all the taskAttachmentImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskAttachmentImages in body.
     */
    @GetMapping("/task-attachment-images")
    public ResponseEntity<List<TaskAttachmentImagesDTO>> getAllTaskAttachmentImages(Pageable pageable) {
        log.debug("REST request to get a page of TaskAttachmentImages");
        Page<TaskAttachmentImagesDTO> page = taskAttachmentImagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-attachment-images/:id} : get the "id" taskAttachmentImages.
     *
     * @param id the id of the taskAttachmentImagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskAttachmentImagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-attachment-images/{id}")
    public ResponseEntity<TaskAttachmentImagesDTO> getTaskAttachmentImages(@PathVariable String id) {
        log.debug("REST request to get TaskAttachmentImages : {}", id);
        Optional<TaskAttachmentImagesDTO> taskAttachmentImagesDTO = taskAttachmentImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskAttachmentImagesDTO);
    }

    /**
     * {@code DELETE  /task-attachment-images/:id} : delete the "id" taskAttachmentImages.
     *
     * @param id the id of the taskAttachmentImagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-attachment-images/{id}")
    public ResponseEntity<Void> deleteTaskAttachmentImages(@PathVariable String id) {
        log.debug("REST request to delete TaskAttachmentImages : {}", id);
        taskAttachmentImagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/task-attachment-images?query=:query} : search for the taskAttachmentImages corresponding
     * to the query.
     *
     * @param query the query of the taskAttachmentImages search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/task-attachment-images")
    public ResponseEntity<List<TaskAttachmentImagesDTO>> searchTaskAttachmentImages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskAttachmentImages for query {}", query);
        Page<TaskAttachmentImagesDTO> page = taskAttachmentImagesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
