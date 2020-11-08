package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.AddressTypesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.AddressTypesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.AddressTypes}.
 */
@RestController
@RequestMapping("/api")
public class AddressTypesResource {

    private final Logger log = LoggerFactory.getLogger(AddressTypesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceAddressTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressTypesService addressTypesService;

    public AddressTypesResource(AddressTypesService addressTypesService) {
        this.addressTypesService = addressTypesService;
    }

    /**
     * {@code POST  /address-types} : Create a new addressTypes.
     *
     * @param addressTypesDTO the addressTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressTypesDTO, or with status {@code 400 (Bad Request)} if the addressTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/address-types")
    public ResponseEntity<AddressTypesDTO> createAddressTypes(@Valid @RequestBody AddressTypesDTO addressTypesDTO) throws URISyntaxException {
        log.debug("REST request to save AddressTypes : {}", addressTypesDTO);
        if (addressTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressTypesDTO result = addressTypesService.save(addressTypesDTO);
        return ResponseEntity.created(new URI("/api/address-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /address-types} : Updates an existing addressTypes.
     *
     * @param addressTypesDTO the addressTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressTypesDTO,
     * or with status {@code 400 (Bad Request)} if the addressTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/address-types")
    public ResponseEntity<AddressTypesDTO> updateAddressTypes(@Valid @RequestBody AddressTypesDTO addressTypesDTO) throws URISyntaxException {
        log.debug("REST request to update AddressTypes : {}", addressTypesDTO);
        if (addressTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressTypesDTO result = addressTypesService.save(addressTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, addressTypesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /address-types} : get all the addressTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addressTypes in body.
     */
    @GetMapping("/address-types")
    public ResponseEntity<List<AddressTypesDTO>> getAllAddressTypes(Pageable pageable) {
        log.debug("REST request to get a page of AddressTypes");
        Page<AddressTypesDTO> page = addressTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /address-types/:id} : get the "id" addressTypes.
     *
     * @param id the id of the addressTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/address-types/{id}")
    public ResponseEntity<AddressTypesDTO> getAddressTypes(@PathVariable String id) {
        log.debug("REST request to get AddressTypes : {}", id);
        Optional<AddressTypesDTO> addressTypesDTO = addressTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressTypesDTO);
    }

    /**
     * {@code DELETE  /address-types/:id} : delete the "id" addressTypes.
     *
     * @param id the id of the addressTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/address-types/{id}")
    public ResponseEntity<Void> deleteAddressTypes(@PathVariable String id) {
        log.debug("REST request to delete AddressTypes : {}", id);
        addressTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/address-types?query=:query} : search for the addressTypes corresponding
     * to the query.
     *
     * @param query the query of the addressTypes search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/address-types")
    public ResponseEntity<List<AddressTypesDTO>> searchAddressTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AddressTypes for query {}", query);
        Page<AddressTypesDTO> page = addressTypesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
