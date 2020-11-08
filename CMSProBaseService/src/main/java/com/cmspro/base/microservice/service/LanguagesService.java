package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Languages;
import com.cmspro.base.microservice.repository.LanguagesRepository;
import com.cmspro.base.microservice.repository.search.LanguagesSearchRepository;
import com.cmspro.base.microservice.service.dto.LanguagesDTO;
import com.cmspro.base.microservice.service.mapper.LanguagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Languages}.
 */
@Service
public class LanguagesService {

    private final Logger log = LoggerFactory.getLogger(LanguagesService.class);

    private final LanguagesRepository languagesRepository;

    private final LanguagesMapper languagesMapper;

    private final LanguagesSearchRepository languagesSearchRepository;

    public LanguagesService(LanguagesRepository languagesRepository, LanguagesMapper languagesMapper, LanguagesSearchRepository languagesSearchRepository) {
        this.languagesRepository = languagesRepository;
        this.languagesMapper = languagesMapper;
        this.languagesSearchRepository = languagesSearchRepository;
    }

    /**
     * Save a languages.
     *
     * @param languagesDTO the entity to save.
     * @return the persisted entity.
     */
    public LanguagesDTO save(LanguagesDTO languagesDTO) {
        log.debug("Request to save Languages : {}", languagesDTO);
        Languages languages = languagesMapper.toEntity(languagesDTO);
        languages = languagesRepository.save(languages);
        LanguagesDTO result = languagesMapper.toDto(languages);
        languagesSearchRepository.save(languages);
        return result;
    }

    /**
     * Get all the languages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<LanguagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Languages");
        return languagesRepository.findAll(pageable)
            .map(languagesMapper::toDto);
    }


    /**
     * Get one languages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<LanguagesDTO> findOne(String id) {
        log.debug("Request to get Languages : {}", id);
        return languagesRepository.findById(id)
            .map(languagesMapper::toDto);
    }

    /**
     * Delete the languages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Languages : {}", id);
        languagesRepository.deleteById(id);
        languagesSearchRepository.deleteById(id);
    }

    /**
     * Search for the languages corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<LanguagesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Languages for query {}", query);
        return languagesSearchRepository.search(queryStringQuery(query), pageable)
            .map(languagesMapper::toDto);
    }
}
