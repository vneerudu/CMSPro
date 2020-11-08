package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.UserGroupsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.UserGroupsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.UserGroups}.
 */
@RestController
@RequestMapping("/api")
public class UserGroupsResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceUserGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupsService userGroupsService;

    public UserGroupsResource(UserGroupsService userGroupsService) {
        this.userGroupsService = userGroupsService;
    }

    /**
     * {@code POST  /user-groups} : Create a new userGroups.
     *
     * @param userGroupsDTO the userGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroupsDTO, or with status {@code 400 (Bad Request)} if the userGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-groups")
    public ResponseEntity<UserGroupsDTO> createUserGroups(@Valid @RequestBody UserGroupsDTO userGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save UserGroups : {}", userGroupsDTO);
        if (userGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroupsDTO result = userGroupsService.save(userGroupsDTO);
        return ResponseEntity.created(new URI("/api/user-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-groups} : Updates an existing userGroups.
     *
     * @param userGroupsDTO the userGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the userGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-groups")
    public ResponseEntity<UserGroupsDTO> updateUserGroups(@Valid @RequestBody UserGroupsDTO userGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update UserGroups : {}", userGroupsDTO);
        if (userGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGroupsDTO result = userGroupsService.save(userGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGroupsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /user-groups} : get all the userGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroups in body.
     */
    @GetMapping("/user-groups")
    public ResponseEntity<List<UserGroupsDTO>> getAllUserGroups(Pageable pageable) {
        log.debug("REST request to get a page of UserGroups");
        Page<UserGroupsDTO> page = userGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-groups/:id} : get the "id" userGroups.
     *
     * @param id the id of the userGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-groups/{id}")
    public ResponseEntity<UserGroupsDTO> getUserGroups(@PathVariable String id) {
        log.debug("REST request to get UserGroups : {}", id);
        Optional<UserGroupsDTO> userGroupsDTO = userGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGroupsDTO);
    }

    /**
     * {@code DELETE  /user-groups/:id} : delete the "id" userGroups.
     *
     * @param id the id of the userGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-groups/{id}")
    public ResponseEntity<Void> deleteUserGroups(@PathVariable String id) {
        log.debug("REST request to delete UserGroups : {}", id);
        userGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/user-groups?query=:query} : search for the userGroups corresponding
     * to the query.
     *
     * @param query the query of the userGroups search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-groups")
    public ResponseEntity<List<UserGroupsDTO>> searchUserGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserGroups for query {}", query);
        Page<UserGroupsDTO> page = userGroupsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
