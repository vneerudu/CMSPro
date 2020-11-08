package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.AccountsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.AccountsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.Accounts}.
 */
@RestController
@RequestMapping("/api")
public class AccountsResource {

    private final Logger log = LoggerFactory.getLogger(AccountsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceAccounts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountsService accountsService;

    public AccountsResource(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    /**
     * {@code POST  /accounts} : Create a new accounts.
     *
     * @param accountsDTO the accountsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountsDTO, or with status {@code 400 (Bad Request)} if the accounts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accounts")
    public ResponseEntity<AccountsDTO> createAccounts(@Valid @RequestBody AccountsDTO accountsDTO) throws URISyntaxException {
        log.debug("REST request to save Accounts : {}", accountsDTO);
        if (accountsDTO.getId() != null) {
            throw new BadRequestAlertException("A new accounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountsDTO result = accountsService.save(accountsDTO);
        return ResponseEntity.created(new URI("/api/accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /accounts} : Updates an existing accounts.
     *
     * @param accountsDTO the accountsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountsDTO,
     * or with status {@code 400 (Bad Request)} if the accountsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accounts")
    public ResponseEntity<AccountsDTO> updateAccounts(@Valid @RequestBody AccountsDTO accountsDTO) throws URISyntaxException {
        log.debug("REST request to update Accounts : {}", accountsDTO);
        if (accountsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountsDTO result = accountsService.save(accountsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, accountsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /accounts} : get all the accounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accounts in body.
     */
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountsDTO>> getAllAccounts(Pageable pageable) {
        log.debug("REST request to get a page of Accounts");
        Page<AccountsDTO> page = accountsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accounts/:id} : get the "id" accounts.
     *
     * @param id the id of the accountsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountsDTO> getAccounts(@PathVariable String id) {
        log.debug("REST request to get Accounts : {}", id);
        Optional<AccountsDTO> accountsDTO = accountsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountsDTO);
    }

    /**
     * {@code DELETE  /accounts/:id} : delete the "id" accounts.
     *
     * @param id the id of the accountsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccounts(@PathVariable String id) {
        log.debug("REST request to delete Accounts : {}", id);
        accountsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/accounts?query=:query} : search for the accounts corresponding
     * to the query.
     *
     * @param query the query of the accounts search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/accounts")
    public ResponseEntity<List<AccountsDTO>> searchAccounts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Accounts for query {}", query);
        Page<AccountsDTO> page = accountsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
