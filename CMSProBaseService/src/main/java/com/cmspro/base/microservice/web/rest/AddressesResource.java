package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.AddressesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.AddressesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.Addresses}.
 */
@RestController
@RequestMapping("/api")
public class AddressesResource {

    private final Logger log = LoggerFactory.getLogger(AddressesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceAddresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressesService addressesService;

    public AddressesResource(AddressesService addressesService) {
        this.addressesService = addressesService;
    }

    /**
     * {@code POST  /addresses} : Create a new addresses.
     *
     * @param addressesDTO the addressesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressesDTO, or with status {@code 400 (Bad Request)} if the addresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/addresses")
    public ResponseEntity<AddressesDTO> createAddresses(@Valid @RequestBody AddressesDTO addressesDTO) throws URISyntaxException {
        log.debug("REST request to save Addresses : {}", addressesDTO);
        if (addressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressesDTO result = addressesService.save(addressesDTO);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /addresses} : Updates an existing addresses.
     *
     * @param addressesDTO the addressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressesDTO,
     * or with status {@code 400 (Bad Request)} if the addressesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/addresses")
    public ResponseEntity<AddressesDTO> updateAddresses(@Valid @RequestBody AddressesDTO addressesDTO) throws URISyntaxException {
        log.debug("REST request to update Addresses : {}", addressesDTO);
        if (addressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressesDTO result = addressesService.save(addressesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, addressesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /addresses} : get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addresses in body.
     */
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressesDTO>> getAllAddresses(Pageable pageable) {
        log.debug("REST request to get a page of Addresses");
        Page<AddressesDTO> page = addressesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /addresses/:id} : get the "id" addresses.
     *
     * @param id the id of the addressesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/addresses/{id}")
    public ResponseEntity<AddressesDTO> getAddresses(@PathVariable String id) {
        log.debug("REST request to get Addresses : {}", id);
        Optional<AddressesDTO> addressesDTO = addressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressesDTO);
    }

    /**
     * {@code DELETE  /addresses/:id} : delete the "id" addresses.
     *
     * @param id the id of the addressesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddresses(@PathVariable String id) {
        log.debug("REST request to delete Addresses : {}", id);
        addressesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/addresses?query=:query} : search for the addresses corresponding
     * to the query.
     *
     * @param query the query of the addresses search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/addresses")
    public ResponseEntity<List<AddressesDTO>> searchAddresses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Addresses for query {}", query);
        Page<AddressesDTO> page = addressesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
