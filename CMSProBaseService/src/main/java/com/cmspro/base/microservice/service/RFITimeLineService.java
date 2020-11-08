package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.RFITimeLine;
import com.cmspro.base.microservice.repository.RFITimeLineRepository;
import com.cmspro.base.microservice.repository.search.RFITimeLineSearchRepository;
import com.cmspro.base.microservice.service.dto.RFITimeLineDTO;
import com.cmspro.base.microservice.service.mapper.RFITimeLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RFITimeLine}.
 */
@Service
public class RFITimeLineService {

    private final Logger log = LoggerFactory.getLogger(RFITimeLineService.class);

    private final RFITimeLineRepository rFITimeLineRepository;

    private final RFITimeLineMapper rFITimeLineMapper;

    private final RFITimeLineSearchRepository rFITimeLineSearchRepository;

    public RFITimeLineService(RFITimeLineRepository rFITimeLineRepository, RFITimeLineMapper rFITimeLineMapper, RFITimeLineSearchRepository rFITimeLineSearchRepository) {
        this.rFITimeLineRepository = rFITimeLineRepository;
        this.rFITimeLineMapper = rFITimeLineMapper;
        this.rFITimeLineSearchRepository = rFITimeLineSearchRepository;
    }

    /**
     * Save a rFITimeLine.
     *
     * @param rFITimeLineDTO the entity to save.
     * @return the persisted entity.
     */
    public RFITimeLineDTO save(RFITimeLineDTO rFITimeLineDTO) {
        log.debug("Request to save RFITimeLine : {}", rFITimeLineDTO);
        RFITimeLine rFITimeLine = rFITimeLineMapper.toEntity(rFITimeLineDTO);
        rFITimeLine = rFITimeLineRepository.save(rFITimeLine);
        RFITimeLineDTO result = rFITimeLineMapper.toDto(rFITimeLine);
        rFITimeLineSearchRepository.save(rFITimeLine);
        return result;
    }

    /**
     * Get all the rFITimeLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RFITimeLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RFITimeLines");
        return rFITimeLineRepository.findAll(pageable)
            .map(rFITimeLineMapper::toDto);
    }


    /**
     * Get one rFITimeLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RFITimeLineDTO> findOne(String id) {
        log.debug("Request to get RFITimeLine : {}", id);
        return rFITimeLineRepository.findById(id)
            .map(rFITimeLineMapper::toDto);
    }

    /**
     * Delete the rFITimeLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RFITimeLine : {}", id);
        rFITimeLineRepository.deleteById(id);
        rFITimeLineSearchRepository.deleteById(id);
    }

    /**
     * Search for the rFITimeLine corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RFITimeLineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RFITimeLines for query {}", query);
        return rFITimeLineSearchRepository.search(queryStringQuery(query), pageable)
            .map(rFITimeLineMapper::toDto);
    }
}
