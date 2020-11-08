package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.UserPermissionsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.UserPermissionsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.UserPermissions}.
 */
@RestController
@RequestMapping("/api")
public class UserPermissionsResource {

    private final Logger log = LoggerFactory.getLogger(UserPermissionsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceUserPermissions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPermissionsService userPermissionsService;

    public UserPermissionsResource(UserPermissionsService userPermissionsService) {
        this.userPermissionsService = userPermissionsService;
    }

    /**
     * {@code POST  /user-permissions} : Create a new userPermissions.
     *
     * @param userPermissionsDTO the userPermissionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPermissionsDTO, or with status {@code 400 (Bad Request)} if the userPermissions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-permissions")
    public ResponseEntity<UserPermissionsDTO> createUserPermissions(@Valid @RequestBody UserPermissionsDTO userPermissionsDTO) throws URISyntaxException {
        log.debug("REST request to save UserPermissions : {}", userPermissionsDTO);
        if (userPermissionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPermissions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPermissionsDTO result = userPermissionsService.save(userPermissionsDTO);
        return ResponseEntity.created(new URI("/api/user-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-permissions} : Updates an existing userPermissions.
     *
     * @param userPermissionsDTO the userPermissionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPermissionsDTO,
     * or with status {@code 400 (Bad Request)} if the userPermissionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPermissionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-permissions")
    public ResponseEntity<UserPermissionsDTO> updateUserPermissions(@Valid @RequestBody UserPermissionsDTO userPermissionsDTO) throws URISyntaxException {
        log.debug("REST request to update UserPermissions : {}", userPermissionsDTO);
        if (userPermissionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserPermissionsDTO result = userPermissionsService.save(userPermissionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userPermissionsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /user-permissions} : get all the userPermissions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPermissions in body.
     */
    @GetMapping("/user-permissions")
    public ResponseEntity<List<UserPermissionsDTO>> getAllUserPermissions(Pageable pageable) {
        log.debug("REST request to get a page of UserPermissions");
        Page<UserPermissionsDTO> page = userPermissionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-permissions/:id} : get the "id" userPermissions.
     *
     * @param id the id of the userPermissionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPermissionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-permissions/{id}")
    public ResponseEntity<UserPermissionsDTO> getUserPermissions(@PathVariable String id) {
        log.debug("REST request to get UserPermissions : {}", id);
        Optional<UserPermissionsDTO> userPermissionsDTO = userPermissionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPermissionsDTO);
    }

    /**
     * {@code DELETE  /user-permissions/:id} : delete the "id" userPermissions.
     *
     * @param id the id of the userPermissionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-permissions/{id}")
    public ResponseEntity<Void> deleteUserPermissions(@PathVariable String id) {
        log.debug("REST request to delete UserPermissions : {}", id);
        userPermissionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/user-permissions?query=:query} : search for the userPermissions corresponding
     * to the query.
     *
     * @param query the query of the userPermissions search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-permissions")
    public ResponseEntity<List<UserPermissionsDTO>> searchUserPermissions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserPermissions for query {}", query);
        Page<UserPermissionsDTO> page = userPermissionsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
