package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.AccountStatusesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.AccountStatusesDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.cmspro.base.microservice.domain.AccountStatuses}.
 */
@RestController
@RequestMapping("/api")
public class AccountStatusesResource {

    private final Logger log = LoggerFactory.getLogger(AccountStatusesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceAccountStatuses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountStatusesService accountStatusesService;

    public AccountStatusesResource(AccountStatusesService accountStatusesService) {
        this.accountStatusesService = accountStatusesService;
    }

    /**
     * {@code POST  /account-statuses} : Create a new accountStatuses.
     *
     * @param accountStatusesDTO the accountStatusesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountStatusesDTO, or with status {@code 400 (Bad Request)} if the accountStatuses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-statuses")
    public ResponseEntity<AccountStatusesDTO> createAccountStatuses(@RequestBody AccountStatusesDTO accountStatusesDTO) throws URISyntaxException {
        log.debug("REST request to save AccountStatuses : {}", accountStatusesDTO);
        if (accountStatusesDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountStatuses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountStatusesDTO result = accountStatusesService.save(accountStatusesDTO);
        return ResponseEntity.created(new URI("/api/account-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /account-statuses} : Updates an existing accountStatuses.
     *
     * @param accountStatusesDTO the accountStatusesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountStatusesDTO,
     * or with status {@code 400 (Bad Request)} if the accountStatusesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountStatusesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-statuses")
    public ResponseEntity<AccountStatusesDTO> updateAccountStatuses(@RequestBody AccountStatusesDTO accountStatusesDTO) throws URISyntaxException {
        log.debug("REST request to update AccountStatuses : {}", accountStatusesDTO);
        if (accountStatusesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountStatusesDTO result = accountStatusesService.save(accountStatusesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, accountStatusesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /account-statuses} : get all the accountStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountStatuses in body.
     */
    @GetMapping("/account-statuses")
    public ResponseEntity<List<AccountStatusesDTO>> getAllAccountStatuses(Pageable pageable) {
        log.debug("REST request to get a page of AccountStatuses");
        Page<AccountStatusesDTO> page = accountStatusesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-statuses/:id} : get the "id" accountStatuses.
     *
     * @param id the id of the accountStatusesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountStatusesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-statuses/{id}")
    public ResponseEntity<AccountStatusesDTO> getAccountStatuses(@PathVariable String id) {
        log.debug("REST request to get AccountStatuses : {}", id);
        Optional<AccountStatusesDTO> accountStatusesDTO = accountStatusesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountStatusesDTO);
    }

    /**
     * {@code DELETE  /account-statuses/:id} : delete the "id" accountStatuses.
     *
     * @param id the id of the accountStatusesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-statuses/{id}")
    public ResponseEntity<Void> deleteAccountStatuses(@PathVariable String id) {
        log.debug("REST request to delete AccountStatuses : {}", id);
        accountStatusesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/account-statuses?query=:query} : search for the accountStatuses corresponding
     * to the query.
     *
     * @param query the query of the accountStatuses search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/account-statuses")
    public ResponseEntity<List<AccountStatusesDTO>> searchAccountStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AccountStatuses for query {}", query);
        Page<AccountStatusesDTO> page = accountStatusesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
