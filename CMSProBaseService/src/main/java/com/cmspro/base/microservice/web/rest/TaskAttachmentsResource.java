package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.TaskAttachmentsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.TaskAttachmentsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.TaskAttachments}.
 */
@RestController
@RequestMapping("/api")
public class TaskAttachmentsResource {

    private final Logger log = LoggerFactory.getLogger(TaskAttachmentsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceTaskAttachments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskAttachmentsService taskAttachmentsService;

    public TaskAttachmentsResource(TaskAttachmentsService taskAttachmentsService) {
        this.taskAttachmentsService = taskAttachmentsService;
    }

    /**
     * {@code POST  /task-attachments} : Create a new taskAttachments.
     *
     * @param taskAttachmentsDTO the taskAttachmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskAttachmentsDTO, or with status {@code 400 (Bad Request)} if the taskAttachments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-attachments")
    public ResponseEntity<TaskAttachmentsDTO> createTaskAttachments(@Valid @RequestBody TaskAttachmentsDTO taskAttachmentsDTO) throws URISyntaxException {
        log.debug("REST request to save TaskAttachments : {}", taskAttachmentsDTO);
        if (taskAttachmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskAttachments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskAttachmentsDTO result = taskAttachmentsService.save(taskAttachmentsDTO);
        return ResponseEntity.created(new URI("/api/task-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /task-attachments} : Updates an existing taskAttachments.
     *
     * @param taskAttachmentsDTO the taskAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the taskAttachmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-attachments")
    public ResponseEntity<TaskAttachmentsDTO> updateTaskAttachments(@Valid @RequestBody TaskAttachmentsDTO taskAttachmentsDTO) throws URISyntaxException {
        log.debug("REST request to update TaskAttachments : {}", taskAttachmentsDTO);
        if (taskAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskAttachmentsDTO result = taskAttachmentsService.save(taskAttachmentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskAttachmentsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /task-attachments} : get all the taskAttachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskAttachments in body.
     */
    @GetMapping("/task-attachments")
    public ResponseEntity<List<TaskAttachmentsDTO>> getAllTaskAttachments(Pageable pageable) {
        log.debug("REST request to get a page of TaskAttachments");
        Page<TaskAttachmentsDTO> page = taskAttachmentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-attachments/:id} : get the "id" taskAttachments.
     *
     * @param id the id of the taskAttachmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskAttachmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-attachments/{id}")
    public ResponseEntity<TaskAttachmentsDTO> getTaskAttachments(@PathVariable String id) {
        log.debug("REST request to get TaskAttachments : {}", id);
        Optional<TaskAttachmentsDTO> taskAttachmentsDTO = taskAttachmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskAttachmentsDTO);
    }

    /**
     * {@code DELETE  /task-attachments/:id} : delete the "id" taskAttachments.
     *
     * @param id the id of the taskAttachmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-attachments/{id}")
    public ResponseEntity<Void> deleteTaskAttachments(@PathVariable String id) {
        log.debug("REST request to delete TaskAttachments : {}", id);
        taskAttachmentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/task-attachments?query=:query} : search for the taskAttachments corresponding
     * to the query.
     *
     * @param query the query of the taskAttachments search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/task-attachments")
    public ResponseEntity<List<TaskAttachmentsDTO>> searchTaskAttachments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskAttachments for query {}", query);
        Page<TaskAttachmentsDTO> page = taskAttachmentsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
