package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.ProjectTeamsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.ProjectTeamsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.ProjectTeams}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTeamsResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTeamsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceProjectTeams";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTeamsService projectTeamsService;

    public ProjectTeamsResource(ProjectTeamsService projectTeamsService) {
        this.projectTeamsService = projectTeamsService;
    }

    /**
     * {@code POST  /project-teams} : Create a new projectTeams.
     *
     * @param projectTeamsDTO the projectTeamsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTeamsDTO, or with status {@code 400 (Bad Request)} if the projectTeams has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-teams")
    public ResponseEntity<ProjectTeamsDTO> createProjectTeams(@Valid @RequestBody ProjectTeamsDTO projectTeamsDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectTeams : {}", projectTeamsDTO);
        if (projectTeamsDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectTeams cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTeamsDTO result = projectTeamsService.save(projectTeamsDTO);
        return ResponseEntity.created(new URI("/api/project-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /project-teams} : Updates an existing projectTeams.
     *
     * @param projectTeamsDTO the projectTeamsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTeamsDTO,
     * or with status {@code 400 (Bad Request)} if the projectTeamsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTeamsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-teams")
    public ResponseEntity<ProjectTeamsDTO> updateProjectTeams(@Valid @RequestBody ProjectTeamsDTO projectTeamsDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectTeams : {}", projectTeamsDTO);
        if (projectTeamsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTeamsDTO result = projectTeamsService.save(projectTeamsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectTeamsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /project-teams} : get all the projectTeams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTeams in body.
     */
    @GetMapping("/project-teams")
    public ResponseEntity<List<ProjectTeamsDTO>> getAllProjectTeams(Pageable pageable) {
        log.debug("REST request to get a page of ProjectTeams");
        Page<ProjectTeamsDTO> page = projectTeamsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-teams/:id} : get the "id" projectTeams.
     *
     * @param id the id of the projectTeamsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTeamsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-teams/{id}")
    public ResponseEntity<ProjectTeamsDTO> getProjectTeams(@PathVariable String id) {
        log.debug("REST request to get ProjectTeams : {}", id);
        Optional<ProjectTeamsDTO> projectTeamsDTO = projectTeamsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTeamsDTO);
    }

    /**
     * {@code DELETE  /project-teams/:id} : delete the "id" projectTeams.
     *
     * @param id the id of the projectTeamsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-teams/{id}")
    public ResponseEntity<Void> deleteProjectTeams(@PathVariable String id) {
        log.debug("REST request to delete ProjectTeams : {}", id);
        projectTeamsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/project-teams?query=:query} : search for the projectTeams corresponding
     * to the query.
     *
     * @param query the query of the projectTeams search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/project-teams")
    public ResponseEntity<List<ProjectTeamsDTO>> searchProjectTeams(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProjectTeams for query {}", query);
        Page<ProjectTeamsDTO> page = projectTeamsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
