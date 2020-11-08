package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.AddressTypes;
import com.cmspro.base.microservice.repository.AddressTypesRepository;
import com.cmspro.base.microservice.repository.search.AddressTypesSearchRepository;
import com.cmspro.base.microservice.service.dto.AddressTypesDTO;
import com.cmspro.base.microservice.service.mapper.AddressTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AddressTypes}.
 */
@Service
public class AddressTypesService {

    private final Logger log = LoggerFactory.getLogger(AddressTypesService.class);

    private final AddressTypesRepository addressTypesRepository;

    private final AddressTypesMapper addressTypesMapper;

    private final AddressTypesSearchRepository addressTypesSearchRepository;

    public AddressTypesService(AddressTypesRepository addressTypesRepository, AddressTypesMapper addressTypesMapper, AddressTypesSearchRepository addressTypesSearchRepository) {
        this.addressTypesRepository = addressTypesRepository;
        this.addressTypesMapper = addressTypesMapper;
        this.addressTypesSearchRepository = addressTypesSearchRepository;
    }

    /**
     * Save a addressTypes.
     *
     * @param addressTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public AddressTypesDTO save(AddressTypesDTO addressTypesDTO) {
        log.debug("Request to save AddressTypes : {}", addressTypesDTO);
        AddressTypes addressTypes = addressTypesMapper.toEntity(addressTypesDTO);
        addressTypes = addressTypesRepository.save(addressTypes);
        AddressTypesDTO result = addressTypesMapper.toDto(addressTypes);
        addressTypesSearchRepository.save(addressTypes);
        return result;
    }

    /**
     * Get all the addressTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AddressTypesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AddressTypes");
        return addressTypesRepository.findAll(pageable)
            .map(addressTypesMapper::toDto);
    }


    /**
     * Get one addressTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AddressTypesDTO> findOne(String id) {
        log.debug("Request to get AddressTypes : {}", id);
        return addressTypesRepository.findById(id)
            .map(addressTypesMapper::toDto);
    }

    /**
     * Delete the addressTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete AddressTypes : {}", id);
        addressTypesRepository.deleteById(id);
        addressTypesSearchRepository.deleteById(id);
    }

    /**
     * Search for the addressTypes corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AddressTypesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AddressTypes for query {}", query);
        return addressTypesSearchRepository.search(queryStringQuery(query), pageable)
            .map(addressTypesMapper::toDto);
    }
}
