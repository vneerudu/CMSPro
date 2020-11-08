package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Addresses;
import com.cmspro.base.microservice.repository.AddressesRepository;
import com.cmspro.base.microservice.repository.search.AddressesSearchRepository;
import com.cmspro.base.microservice.service.dto.AddressesDTO;
import com.cmspro.base.microservice.service.mapper.AddressesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Addresses}.
 */
@Service
public class AddressesService {

    private final Logger log = LoggerFactory.getLogger(AddressesService.class);

    private final AddressesRepository addressesRepository;

    private final AddressesMapper addressesMapper;

    private final AddressesSearchRepository addressesSearchRepository;

    public AddressesService(AddressesRepository addressesRepository, AddressesMapper addressesMapper, AddressesSearchRepository addressesSearchRepository) {
        this.addressesRepository = addressesRepository;
        this.addressesMapper = addressesMapper;
        this.addressesSearchRepository = addressesSearchRepository;
    }

    /**
     * Save a addresses.
     *
     * @param addressesDTO the entity to save.
     * @return the persisted entity.
     */
    public AddressesDTO save(AddressesDTO addressesDTO) {
        log.debug("Request to save Addresses : {}", addressesDTO);
        Addresses addresses = addressesMapper.toEntity(addressesDTO);
        addresses = addressesRepository.save(addresses);
        AddressesDTO result = addressesMapper.toDto(addresses);
        addressesSearchRepository.save(addresses);
        return result;
    }

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AddressesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressesRepository.findAll(pageable)
            .map(addressesMapper::toDto);
    }


    /**
     * Get one addresses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AddressesDTO> findOne(String id) {
        log.debug("Request to get Addresses : {}", id);
        return addressesRepository.findById(id)
            .map(addressesMapper::toDto);
    }

    /**
     * Delete the addresses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Addresses : {}", id);
        addressesRepository.deleteById(id);
        addressesSearchRepository.deleteById(id);
    }

    /**
     * Search for the addresses corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AddressesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Addresses for query {}", query);
        return addressesSearchRepository.search(queryStringQuery(query), pageable)
            .map(addressesMapper::toDto);
    }
}
