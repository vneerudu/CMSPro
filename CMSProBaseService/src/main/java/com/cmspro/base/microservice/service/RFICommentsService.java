package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.RFIComments;
import com.cmspro.base.microservice.repository.RFICommentsRepository;
import com.cmspro.base.microservice.repository.search.RFICommentsSearchRepository;
import com.cmspro.base.microservice.service.dto.RFICommentsDTO;
import com.cmspro.base.microservice.service.mapper.RFICommentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RFIComments}.
 */
@Service
public class RFICommentsService {

    private final Logger log = LoggerFactory.getLogger(RFICommentsService.class);

    private final RFICommentsRepository rFICommentsRepository;

    private final RFICommentsMapper rFICommentsMapper;

    private final RFICommentsSearchRepository rFICommentsSearchRepository;

    public RFICommentsService(RFICommentsRepository rFICommentsRepository, RFICommentsMapper rFICommentsMapper, RFICommentsSearchRepository rFICommentsSearchRepository) {
        this.rFICommentsRepository = rFICommentsRepository;
        this.rFICommentsMapper = rFICommentsMapper;
        this.rFICommentsSearchRepository = rFICommentsSearchRepository;
    }

    /**
     * Save a rFIComments.
     *
     * @param rFICommentsDTO the entity to save.
     * @return the persisted entity.
     */
    public RFICommentsDTO save(RFICommentsDTO rFICommentsDTO) {
        log.debug("Request to save RFIComments : {}", rFICommentsDTO);
        RFIComments rFIComments = rFICommentsMapper.toEntity(rFICommentsDTO);
        rFIComments = rFICommentsRepository.save(rFIComments);
        RFICommentsDTO result = rFICommentsMapper.toDto(rFIComments);
        rFICommentsSearchRepository.save(rFIComments);
        return result;
    }

    /**
     * Get all the rFIComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RFICommentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RFIComments");
        return rFICommentsRepository.findAll(pageable)
            .map(rFICommentsMapper::toDto);
    }


    /**
     * Get one rFIComments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RFICommentsDTO> findOne(String id) {
        log.debug("Request to get RFIComments : {}", id);
        return rFICommentsRepository.findById(id)
            .map(rFICommentsMapper::toDto);
    }

    /**
     * Delete the rFIComments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RFIComments : {}", id);
        rFICommentsRepository.deleteById(id);
        rFICommentsSearchRepository.deleteById(id);
    }

    /**
     * Search for the rFIComments corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RFICommentsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RFIComments for query {}", query);
        return rFICommentsSearchRepository.search(queryStringQuery(query), pageable)
            .map(rFICommentsMapper::toDto);
    }
}
