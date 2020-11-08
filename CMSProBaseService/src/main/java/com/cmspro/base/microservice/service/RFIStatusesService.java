package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.RFIStatuses;
import com.cmspro.base.microservice.repository.RFIStatusesRepository;
import com.cmspro.base.microservice.repository.search.RFIStatusesSearchRepository;
import com.cmspro.base.microservice.service.dto.RFIStatusesDTO;
import com.cmspro.base.microservice.service.mapper.RFIStatusesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RFIStatuses}.
 */
@Service
public class RFIStatusesService {

    private final Logger log = LoggerFactory.getLogger(RFIStatusesService.class);

    private final RFIStatusesRepository rFIStatusesRepository;

    private final RFIStatusesMapper rFIStatusesMapper;

    private final RFIStatusesSearchRepository rFIStatusesSearchRepository;

    public RFIStatusesService(RFIStatusesRepository rFIStatusesRepository, RFIStatusesMapper rFIStatusesMapper, RFIStatusesSearchRepository rFIStatusesSearchRepository) {
        this.rFIStatusesRepository = rFIStatusesRepository;
        this.rFIStatusesMapper = rFIStatusesMapper;
        this.rFIStatusesSearchRepository = rFIStatusesSearchRepository;
    }

    /**
     * Save a rFIStatuses.
     *
     * @param rFIStatusesDTO the entity to save.
     * @return the persisted entity.
     */
    public RFIStatusesDTO save(RFIStatusesDTO rFIStatusesDTO) {
        log.debug("Request to save RFIStatuses : {}", rFIStatusesDTO);
        RFIStatuses rFIStatuses = rFIStatusesMapper.toEntity(rFIStatusesDTO);
        rFIStatuses = rFIStatusesRepository.save(rFIStatuses);
        RFIStatusesDTO result = rFIStatusesMapper.toDto(rFIStatuses);
        rFIStatusesSearchRepository.save(rFIStatuses);
        return result;
    }

    /**
     * Get all the rFIStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RFIStatusesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RFIStatuses");
        return rFIStatusesRepository.findAll(pageable)
            .map(rFIStatusesMapper::toDto);
    }



    /**
     *  Get all the rFIStatuses where Rfi is {@code null}.
     *  @return the list of entities.
     */
    public List<RFIStatusesDTO> findAllWhereRfiIsNull() {
        log.debug("Request to get all rFIStatuses where Rfi is null");
        return StreamSupport
            .stream(rFIStatusesRepository.findAll().spliterator(), false)
            .filter(rFIStatuses -> rFIStatuses.getRfi() == null)
            .map(rFIStatusesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rFIStatuses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RFIStatusesDTO> findOne(String id) {
        log.debug("Request to get RFIStatuses : {}", id);
        return rFIStatusesRepository.findById(id)
            .map(rFIStatusesMapper::toDto);
    }

    /**
     * Delete the rFIStatuses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RFIStatuses : {}", id);
        rFIStatusesRepository.deleteById(id);
        rFIStatusesSearchRepository.deleteById(id);
    }

    /**
     * Search for the rFIStatuses corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RFIStatusesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RFIStatuses for query {}", query);
        return rFIStatusesSearchRepository.search(queryStringQuery(query), pageable)
            .map(rFIStatusesMapper::toDto);
    }
}
