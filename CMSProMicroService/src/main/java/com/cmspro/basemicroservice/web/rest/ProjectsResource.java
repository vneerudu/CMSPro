package com.cmspro.basemicroservice.web.rest;

import com.cmspro.basemicroservice.service.ProjectsService;
import com.cmspro.basemicroservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.basemicroservice.service.dto.ProjectsDTO;
import com.cmspro.basemicroservice.service.dto.ProjectsCriteria;
import com.cmspro.basemicroservice.service.ProjectsQueryService;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
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
 * REST controller for managing {@link com.cmspro.basemicroservice.domain.Projects}.
 */
@RestController
@RequestMapping("/api")
public class ProjectsResource {

    private final Logger log = LoggerFactory.getLogger(ProjectsResource.class);

    private static final String ENTITY_NAME = "cmsProMicroServiceProjects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectsService projectsService;

    private final ProjectsQueryService projectsQueryService;

    public ProjectsResource(ProjectsService projectsService, ProjectsQueryService projectsQueryService) {
        this.projectsService = projectsService;
        this.projectsQueryService = projectsQueryService;
    }

    /**
     * {@code POST  /projects} : Create a new projects.
     *
     * @param projectsDTO the projectsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectsDTO, or with status {@code 400 (Bad Request)} if the projects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projects")
    public ResponseEntity<ProjectsDTO> createProjects(@Valid @RequestBody ProjectsDTO projectsDTO) throws URISyntaxException {
        log.debug("REST request to save Projects : {}", projectsDTO);
        if (projectsDTO.getId() != null) {
            throw new BadRequestAlertException("A new projects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectsDTO result = projectsService.save(projectsDTO);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projects} : Updates an existing projects.
     *
     * @param projectsDTO the projectsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectsDTO,
     * or with status {@code 400 (Bad Request)} if the projectsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projects")
    public ResponseEntity<ProjectsDTO> updateProjects(@Valid @RequestBody ProjectsDTO projectsDTO) throws URISyntaxException {
        log.debug("REST request to update Projects : {}", projectsDTO);
        if (projectsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectsDTO result = projectsService.save(projectsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /projects} : get all the projects.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projects in body.
     */
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectsDTO>> getAllProjects(ProjectsCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Projects by criteria: {}", criteria);
        Page<ProjectsDTO> page = projectsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /projects/count} : count all the projects.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/projects/count")
    public ResponseEntity<Long> countProjects(ProjectsCriteria criteria) {
        log.debug("REST request to count Projects by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /projects/:id} : get the "id" projects.
     *
     * @param id the id of the projectsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectsDTO> getProjects(@PathVariable Long id) {
        log.debug("REST request to get Projects : {}", id);
        Optional<ProjectsDTO> projectsDTO = projectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectsDTO);
    }

    /**
     * {@code DELETE  /projects/:id} : delete the "id" projects.
     *
     * @param id the id of the projectsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProjects(@PathVariable Long id) {
        log.debug("REST request to delete Projects : {}", id);
        projectsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/projects?query=:query} : search for the projects corresponding
     * to the query.
     *
     * @param query the query of the projects search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/projects")
    public ResponseEntity<List<ProjectsDTO>> searchProjects(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Projects for query {}", query);
        Page<ProjectsDTO> page = projectsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
