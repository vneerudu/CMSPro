package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.UserRolesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.UserRolesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.UserRoles}.
 */
@RestController
@RequestMapping("/api")
public class UserRolesResource {

    private final Logger log = LoggerFactory.getLogger(UserRolesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceUserRoles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRolesService userRolesService;

    public UserRolesResource(UserRolesService userRolesService) {
        this.userRolesService = userRolesService;
    }

    /**
     * {@code POST  /user-roles} : Create a new userRoles.
     *
     * @param userRolesDTO the userRolesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRolesDTO, or with status {@code 400 (Bad Request)} if the userRoles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-roles")
    public ResponseEntity<UserRolesDTO> createUserRoles(@Valid @RequestBody UserRolesDTO userRolesDTO) throws URISyntaxException {
        log.debug("REST request to save UserRoles : {}", userRolesDTO);
        if (userRolesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userRoles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRolesDTO result = userRolesService.save(userRolesDTO);
        return ResponseEntity.created(new URI("/api/user-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-roles} : Updates an existing userRoles.
     *
     * @param userRolesDTO the userRolesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRolesDTO,
     * or with status {@code 400 (Bad Request)} if the userRolesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRolesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-roles")
    public ResponseEntity<UserRolesDTO> updateUserRoles(@Valid @RequestBody UserRolesDTO userRolesDTO) throws URISyntaxException {
        log.debug("REST request to update UserRoles : {}", userRolesDTO);
        if (userRolesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserRolesDTO result = userRolesService.save(userRolesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userRolesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /user-roles} : get all the userRoles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRoles in body.
     */
    @GetMapping("/user-roles")
    public ResponseEntity<List<UserRolesDTO>> getAllUserRoles(Pageable pageable) {
        log.debug("REST request to get a page of UserRoles");
        Page<UserRolesDTO> page = userRolesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-roles/:id} : get the "id" userRoles.
     *
     * @param id the id of the userRolesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRolesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-roles/{id}")
    public ResponseEntity<UserRolesDTO> getUserRoles(@PathVariable String id) {
        log.debug("REST request to get UserRoles : {}", id);
        Optional<UserRolesDTO> userRolesDTO = userRolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRolesDTO);
    }

    /**
     * {@code DELETE  /user-roles/:id} : delete the "id" userRoles.
     *
     * @param id the id of the userRolesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-roles/{id}")
    public ResponseEntity<Void> deleteUserRoles(@PathVariable String id) {
        log.debug("REST request to delete UserRoles : {}", id);
        userRolesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/user-roles?query=:query} : search for the userRoles corresponding
     * to the query.
     *
     * @param query the query of the userRoles search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-roles")
    public ResponseEntity<List<UserRolesDTO>> searchUserRoles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserRoles for query {}", query);
        Page<UserRolesDTO> page = userRolesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
