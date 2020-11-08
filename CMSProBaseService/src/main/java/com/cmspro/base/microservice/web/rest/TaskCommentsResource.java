package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.TaskCommentsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.TaskCommentsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.TaskComments}.
 */
@RestController
@RequestMapping("/api")
public class TaskCommentsResource {

    private final Logger log = LoggerFactory.getLogger(TaskCommentsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceTaskComments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskCommentsService taskCommentsService;

    public TaskCommentsResource(TaskCommentsService taskCommentsService) {
        this.taskCommentsService = taskCommentsService;
    }

    /**
     * {@code POST  /task-comments} : Create a new taskComments.
     *
     * @param taskCommentsDTO the taskCommentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskCommentsDTO, or with status {@code 400 (Bad Request)} if the taskComments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-comments")
    public ResponseEntity<TaskCommentsDTO> createTaskComments(@Valid @RequestBody TaskCommentsDTO taskCommentsDTO) throws URISyntaxException {
        log.debug("REST request to save TaskComments : {}", taskCommentsDTO);
        if (taskCommentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskComments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskCommentsDTO result = taskCommentsService.save(taskCommentsDTO);
        return ResponseEntity.created(new URI("/api/task-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /task-comments} : Updates an existing taskComments.
     *
     * @param taskCommentsDTO the taskCommentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskCommentsDTO,
     * or with status {@code 400 (Bad Request)} if the taskCommentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskCommentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-comments")
    public ResponseEntity<TaskCommentsDTO> updateTaskComments(@Valid @RequestBody TaskCommentsDTO taskCommentsDTO) throws URISyntaxException {
        log.debug("REST request to update TaskComments : {}", taskCommentsDTO);
        if (taskCommentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskCommentsDTO result = taskCommentsService.save(taskCommentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskCommentsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /task-comments} : get all the taskComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskComments in body.
     */
    @GetMapping("/task-comments")
    public ResponseEntity<List<TaskCommentsDTO>> getAllTaskComments(Pageable pageable) {
        log.debug("REST request to get a page of TaskComments");
        Page<TaskCommentsDTO> page = taskCommentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-comments/:id} : get the "id" taskComments.
     *
     * @param id the id of the taskCommentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskCommentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-comments/{id}")
    public ResponseEntity<TaskCommentsDTO> getTaskComments(@PathVariable String id) {
        log.debug("REST request to get TaskComments : {}", id);
        Optional<TaskCommentsDTO> taskCommentsDTO = taskCommentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskCommentsDTO);
    }

    /**
     * {@code DELETE  /task-comments/:id} : delete the "id" taskComments.
     *
     * @param id the id of the taskCommentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-comments/{id}")
    public ResponseEntity<Void> deleteTaskComments(@PathVariable String id) {
        log.debug("REST request to delete TaskComments : {}", id);
        taskCommentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/task-comments?query=:query} : search for the taskComments corresponding
     * to the query.
     *
     * @param query the query of the taskComments search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/task-comments")
    public ResponseEntity<List<TaskCommentsDTO>> searchTaskComments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskComments for query {}", query);
        Page<TaskCommentsDTO> page = taskCommentsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
