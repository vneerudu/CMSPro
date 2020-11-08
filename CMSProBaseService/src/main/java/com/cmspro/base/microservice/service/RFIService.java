package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.RFI;
import com.cmspro.base.microservice.repository.RFIRepository;
import com.cmspro.base.microservice.repository.search.RFISearchRepository;
import com.cmspro.base.microservice.service.dto.RFIDTO;
import com.cmspro.base.microservice.service.mapper.RFIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RFI}.
 */
@Service
public class RFIService {

    private final Logger log = LoggerFactory.getLogger(RFIService.class);

    private final RFIRepository rFIRepository;

    private final RFIMapper rFIMapper;

    private final RFISearchRepository rFISearchRepository;

    public RFIService(RFIRepository rFIRepository, RFIMapper rFIMapper, RFISearchRepository rFISearchRepository) {
        this.rFIRepository = rFIRepository;
        this.rFIMapper = rFIMapper;
        this.rFISearchRepository = rFISearchRepository;
    }

    /**
     * Save a rFI.
     *
     * @param rFIDTO the entity to save.
     * @return the persisted entity.
     */
    public RFIDTO save(RFIDTO rFIDTO) {
        log.debug("Request to save RFI : {}", rFIDTO);
        RFI rFI = rFIMapper.toEntity(rFIDTO);
        rFI = rFIRepository.save(rFI);
        RFIDTO result = rFIMapper.toDto(rFI);
        rFISearchRepository.save(rFI);
        return result;
    }

    /**
     * Get all the rFIS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RFIDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RFIS");
        return rFIRepository.findAll(pageable)
            .map(rFIMapper::toDto);
    }


    /**
     * Get one rFI by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RFIDTO> findOne(String id) {
        log.debug("Request to get RFI : {}", id);
        return rFIRepository.findById(id)
            .map(rFIMapper::toDto);
    }

    /**
     * Delete the rFI by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RFI : {}", id);
        rFIRepository.deleteById(id);
        rFISearchRepository.deleteById(id);
    }

    /**
     * Search for the rFI corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RFIDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RFIS for query {}", query);
        return rFISearchRepository.search(queryStringQuery(query), pageable)
            .map(rFIMapper::toDto);
    }
}
